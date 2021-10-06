package com.products.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@DynamoDBTable(tableName = "products_table")
public class Product {
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private Double price;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static String getProductsJson(List<Product> productsList) {
        Gson gson = new Gson();
        return gson.toJson(productsList);
    }

    public static Product getProduct(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Product.class);
    }
}
