package com.maktabsharif.entity;

public class Transaction extends BaseEntity{

    String id;
    Long transactionDate;
    Double transactionAmount;
    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
