package com.smartshop.beugro.product;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Product {
    private long id;
    private String name;
    private int qty;
    private String sku;
    private int status;
    private String statusText;
    private Timestamp timestamp;
    private String timestampFormatted;

    public Product() {
    }

    public Product(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }

    public Product(long id,String name, int qty, String sku) {
        this.id=id;
        this.name = name;
        this.qty = qty;
        this.sku = sku;
    }

    public Product(long id, String name, String sku, int qty, int status, Timestamp timestamp) {
        this.id=id;
        this.name = name;
        this.qty = qty;
        this.sku=sku;
        if(status==1) {
            this.statusText = "Elérhető";
        }else{
            this.statusText="Kifogyott";
        }
        timestampFormatted = new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss").format(timestamp);

    }
    public void setUp(){
        timestamp = Timestamp.valueOf(LocalDateTime.now());
        status = 1;
        String nameChanged = Normalizer.normalize(this.name, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");

        sku = nameChanged.substring(0,3) + timestamp.getSeconds()+timestamp.getMinutes() + status;
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

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampFormatted() {
        return timestampFormatted;
    }

    public void setTimestampFormatted(String timestampFormatted) {
        this.timestampFormatted = timestampFormatted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
