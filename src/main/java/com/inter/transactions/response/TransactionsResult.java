package com.inter.transactions.response;

import java.util.List;

/**
 * 
 */
public class TransactionsResult {

    private List<TransactionStatementResponse> transactionStatementResponses;

    private TransactionAverageStatementResponse transactionAverageStatementResponse;

    public List<TransactionStatementResponse> getTransactionStatementResponses() {
        return transactionStatementResponses;
    }

    public void setTransactionStatementResponses(List<TransactionStatementResponse> transactionStatementResponses) {
        this.transactionStatementResponses = transactionStatementResponses;
    }

    public TransactionAverageStatementResponse getTransactionAverageStatementResponse() {
        return transactionAverageStatementResponse;
    }

    public void setTransactionAverageStatementResponse(TransactionAverageStatementResponse transactionAverageStatementResponse) {
        this.transactionAverageStatementResponse = transactionAverageStatementResponse;
    }
}
