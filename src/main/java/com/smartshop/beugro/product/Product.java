package com.smartshop.beugro.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Product {
    private String name;
    private int qty;
    private String sku;
    private int status;
    private Timestamp timestamp;

    public Product(String name, int qty) {
        this.name = name;
        this.qty = qty;
        timestamp = Timestamp.valueOf(LocalDateTime.now());
        status = 1;
        sku = name.substring(0,3) + timestamp.getSeconds()+timestamp.getMinutes() + status;

    }

    public Product(String name, int qty, String sku, int status, Timestamp timestamp) {
        this.name = name;
        this.qty = qty;
        this.sku = sku;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSku() {
        return sku;
    }

    public int getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
