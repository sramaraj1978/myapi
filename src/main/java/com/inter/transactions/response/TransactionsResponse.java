package com.inter.transactions.response;

import java.util.List;

/**
 * 
 */
public class TransactionsResponse {

    private String error;
    private List<Transactions> transactions;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }
}
