package com.inter.transactions.response;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactions implements Comparable<Transactions> {

    private double amount;

    @SerializedName("is-pending")
    private boolean isPending;

    @SerializedName("aggregation-time")
    private long aggregationTime;

    @SerializedName("account-id")
    private String accountId;

    @SerializedName("clear-date")
    private long clearDate;

    @SerializedName("transaction-id")
    private String transactionId;

    @SerializedName("raw-merchant")
    private String rawMerchant;

    private String categorization;

    private String merchant;

    @SerializedName("transaction-time")
    private String transactionTime;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public long getAggregationTime() {
        return aggregationTime;
    }

    public void setAggregationTime(long aggregationTime) {
        this.aggregationTime = aggregationTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getClearDate() {
        return clearDate;
    }

    public void setClearDate(long clearDate) {
        this.clearDate = clearDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public void setRawMerchant(String rawMerchant) {
        this.rawMerchant = rawMerchant;
    }

    public String getCategorization() {
        return categorization;
    }

    public void setCategorization(String categorization) {
        this.categorization = categorization;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public int compareTo(Transactions transactions) {

        return convertDate(getTransactionTime()).compareTo(convertDate(transactions.getTransactionTime()));
    }

    private Date convertDate(String transactionTime) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            return formatter.parse(transactionTime);
        } catch (ParseException e) {

        }
        return null;
    }
}