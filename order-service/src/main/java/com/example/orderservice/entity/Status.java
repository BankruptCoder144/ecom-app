package com.example.orderservice.entity;

public enum Status {
    
    PENDING("PENDING"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS");

    // other Order Status like IN-TRANSIT, PAID etc..

    Status(String status) {
    }
}
