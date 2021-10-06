package com.products.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.products.lambda.model.Product;
import com.products.lambda.queue.ProductQueue;
import com.products.lambda.s3.ProductS3;
import org.apache.log4j.Logger;

public class ProductsS3Handler implements RequestHandler<S3Event, String> {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductS3 productS3;
    private final ProductQueue productQueue;

    public ProductsS3Handler() {
        productS3 = ProductS3.getInstance();
        productQueue = ProductQueue.getInstance();
    }

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);
        String srcBucket = record.getS3().getBucket().getName();
        String srcKey = record.getS3().getObject().getUrlDecodedKey();

        Product product = productS3.getProduct(srcBucket, srcKey);
        product.setName("sqs_" + product.getName());
        String productMessage = product.toString();
        productQueue.sendMessage(productMessage);

        return null;
    }
}
