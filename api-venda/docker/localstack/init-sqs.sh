#!/bin/sh
echo "Criando filas no SQS LocalStack..."
awslocal sqs create-queue --queue-name compra_cartao_credito
awslocal sqs create-queue --queue-name compra_cartao_credito_aprovada
