package com.thamiris.api_banco.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompraCartaoCreditoListener {

    private final SqsClient sqsClient;

    @Value("${cloud.aws.fila.compra_cartao_credito}")
    private String queueUrlCompraCartaoCredito;

    @Value("${cloud.aws.fila.compra_cartao_credito_aprovada}")
    private String queueUrlCompraCartaoCreditoAprovada;

    @Scheduled(fixedDelay = 10000)
    public void receiveMessage() {
        try {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrlCompraCartaoCredito)
                    .maxNumberOfMessages(5)
                    .waitTimeSeconds(5)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            for (Message message : messages) {
                log.info("Mensagem recebida do SQS - Cartão de Crédito: {}", message.body());

                envioMensagemFilaNovaSolicitacaoInconsistenteSalesForceRecebida(message.body());

                DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrlCompraCartaoCredito)
                        .receiptHandle(message.receiptHandle())
                        .build();

                sqsClient.deleteMessage(deleteRequest);
            }
        } catch (Exception e) {
            log.error("Erro ao processar mensagens do SQS", e);
        }
    }

    public void envioMensagemFilaNovaSolicitacaoInconsistenteSalesForceRecebida(String mensagem) {
        SendMessageRequest sendRequest = SendMessageRequest.builder()
                .queueUrl(queueUrlCompraCartaoCreditoAprovada)
                .messageBody(mensagem)
                .build();

        sqsClient.sendMessage(sendRequest);
        log.info("Mensagem enviada para a fila de aprovação: {}", queueUrlCompraCartaoCreditoAprovada);
    }
}
