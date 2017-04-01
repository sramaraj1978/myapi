package com.inter.transactions.response;

/**
 * 
 */
public class TransactionAverageStatementResponse {

    private String average;
    private MonthlyStatement monthlyStatement;

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public MonthlyStatement getMonthlyStatement() {
        return monthlyStatement;
    }

    public void setMonthlyStatement(MonthlyStatement monthlyStatement) {
        this.monthlyStatement = monthlyStatement;
    }
}
