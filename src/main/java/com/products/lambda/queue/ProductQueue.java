package com.products.lambda.queue;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class ProductQueue {
    private static final String QUEUE_URL = System.getenv("QUEUE_URL");

    private static ProductQueue productQueue;

    private AmazonSQS sqs;

    public ProductQueue() {
        sqs = AmazonSQSClientBuilder.defaultClient();
    }

    public static ProductQueue getInstance() {
        if (productQueue == null) {
            productQueue = new ProductQueue();
        }

        return productQueue;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(message)
                .withDelaySeconds(5);

        sqs.sendMessage(sendMsgRequest);
    }
}
