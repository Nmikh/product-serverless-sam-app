package com.products.lambda.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.products.lambda.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ProductS3 {
    private static ProductS3 productS3;

    private AmazonS3 s3Client;

    public ProductS3() {
        s3Client = AmazonS3ClientBuilder.defaultClient();
    }

    public static ProductS3 getInstance() {
        if (productS3 == null) {
            productS3 = new ProductS3();
        }

        return productS3;
    }

    public Product getProduct(String srcBucket, String srcKey) {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
        InputStream productData = s3Object.getObjectContent();

        String productJson;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(productData))) {
            productJson = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return null;
        }

        return Product.getProduct(productJson);
    }
}
