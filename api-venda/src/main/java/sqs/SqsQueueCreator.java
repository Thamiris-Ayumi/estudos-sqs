package sqs;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;

import java.net.URI;

public class SqsQueueCreator {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
                ))
                .build();

        // Criando a primeira fila
        createQueue(sqsClient, "compra_cartao_credito");

        // Criando a segunda fila
        createQueue(sqsClient, "compra_cartao_credito_aprovada");

        sqsClient.close();
    }

    private static void createQueue(SqsClient sqsClient, String queueName) {
        CreateQueueRequest request = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();

        CreateQueueResponse response = sqsClient.createQueue(request);
        System.out.println("Fila criada: " + response.queueUrl());
    }
}
