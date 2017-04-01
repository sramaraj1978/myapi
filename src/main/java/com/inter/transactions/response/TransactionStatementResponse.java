package com.inter.transactions.response;

import java.util.List;

/**
 * 
 */
public class TransactionStatementResponse {

    private String yearMonth;
    private MonthlyStatement monthlyStatement;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public MonthlyStatement getMonthlyStatement() {
        return monthlyStatement;
    }

    public void setMonthlyStatement(MonthlyStatement monthlyStatement) {
        this.monthlyStatement = monthlyStatement;
    }
}
