package sqs;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;

import java.net.URI;

public class ListSqsQueues {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
                ))
                .build();

        ListQueuesRequest request = ListQueuesRequest.builder().build();
        ListQueuesResponse response = sqsClient.listQueues(request);

        System.out.println("Filas disponíveis no SQS:");
        response.queueUrls().forEach(System.out::println);

        sqsClient.close();
    }
}
