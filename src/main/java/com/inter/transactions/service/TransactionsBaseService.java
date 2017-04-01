package com.inter.transactions.service;

import com.inter.transactions.request.GenericRequest;
import com.inter.transactions.response.*;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sramara3 on 4/1/2017.
 */
public class TransactionsBaseService {

    protected TransactionsResult prepareStatement(TransactionsResponse transactionsResponse, boolean filterDonut) throws ParseException {
        List<Transactions> transactionsList = transactionsResponse.getTransactions();

        Set<Transactions> monthlyTransactions = new HashSet();
        Map<String, Set<Transactions>> filteredTransactions = new TreeMap();
        Calendar previousCalendar = Calendar.getInstance();
        Calendar currentCalendar  = Calendar.getInstance();

        int count = 0;
        for (Transactions transactions: transactionsList) {
            currentCalendar = convertToCalendarValue(transactions.getTransactionTime());
            if(isSameYearMonth(currentCalendar, previousCalendar)) {
                monthlyTransactions.add(transactions);
                count ++;
            } else if(count > 0){
                filteredTransactions.put(currentCalendar.get(Calendar.YEAR) + "-" + formatCalanderMonth(currentCalendar) + "", monthlyTransactions);
                monthlyTransactions = new HashSet();
            }
            previousCalendar = currentCalendar;
        }
        filteredTransactions.put(currentCalendar.get(Calendar.YEAR) + "-" + formatCalanderMonth(currentCalendar) + "", monthlyTransactions);
        TransactionsResult transactionsResult = getTransactionsResult(filterDonut, filteredTransactions);
        return transactionsResult;
    }

    private TransactionsResult getTransactionsResult(boolean filterDonut, Map<String, Set<Transactions>> filteredTransactions) {
        double spent = 0;
        double income = 0;
        double averageSpent = 0;
        double averageIncome = 0;
        TransactionsResult transactionsResult = new TransactionsResult();
        TransactionStatementResponse transactionStatementResponse;
        TransactionAverageStatementResponse transactionAverageStatementResponse = new TransactionAverageStatementResponse();
        List<TransactionStatementResponse> transactionStatementResponses = new ArrayList<TransactionStatementResponse>();
        MonthlyStatement monthlyStatement;
        for (Map.Entry entry : filteredTransactions.entrySet()) {
            Set<Transactions> transactionsSet = (Set)entry.getValue();
            for (Transactions transactions: transactionsSet) {
                if(transactions.getAmount() < 0 ) {
                    if(filterDonut) {
                        if(!transactions.getMerchant().equals("Krispy Kreme Donuts") &&
                                !transactions.getMerchant().startsWith("DUNKIN")) {
                            spent += transactions.getAmount() / 10000;
                        }
                    } else {
                        spent += transactions.getAmount() / 10000;
                    }
                } else {
                    income+= transactions.getAmount()/10000;
                }
            }
            monthlyStatement = new MonthlyStatement();
            monthlyStatement.setSpent("$"+roundTwoDecimals(spent));
            monthlyStatement.setIncome("$"+roundTwoDecimals(income));
            transactionStatementResponse =  new TransactionStatementResponse();
            transactionStatementResponse.setYearMonth(entry.getKey().toString());
            transactionStatementResponse.setMonthlyStatement(monthlyStatement);
            transactionStatementResponses.add(transactionStatementResponse);
            averageSpent+= spent;
            averageIncome+= income;
            spent = 0;
            income = 0;

        }
        transactionsResult.setTransactionStatementResponses(transactionStatementResponses);
        monthlyStatement = new MonthlyStatement();
        monthlyStatement.setIncome(calculateAverage(averageIncome, filteredTransactions.size()));
        monthlyStatement.setSpent(calculateAverage(averageSpent, filteredTransactions.size()));
        transactionAverageStatementResponse.setAverage("average");
        transactionAverageStatementResponse.setMonthlyStatement(monthlyStatement);
        transactionsResult.setTransactionAverageStatementResponse(transactionAverageStatementResponse);
        return transactionsResult;
    }

    private String calculateAverage(double average, int size) {
        return "$" + roundTwoDecimals(average/size);
    }

    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    private int formatCalanderMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH)+ 1;
    }

    private Calendar convertToCalendarValue (String transactionTime) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(transactionTime));
        return calendar;
    }

    private boolean isSameYearMonth(Calendar cal1, Calendar cal2) {
        /*if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }*/
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }


    protected Response makeServiceCall(GenericRequest transactionsRequest, String method) throws IOException {
        WebClient baseClient = createWebClient("https://2016.api.levelmoney.com");
        baseClient.path("/api/v2/core/get-all-transactions");
        Response response;
        String result = "";
        try {
            if (HttpMethod.PUT.equals(method)) {
                response = baseClient.put(transactionsRequest);
            } else if (HttpMethod.GET.equals(method)) {
                response = baseClient.get();
            } else {
                response = baseClient.post(transactionsRequest);
            }
            /*sourceResponse.setResponseCode(response.getStatus());*/
            InputStream inputStream = null;
            StringWriter writer = null;
            try {
                inputStream = (InputStream) response.getEntity();
                writer = new StringWriter();
                IOUtils.copy(inputStream, writer);
                result = writer.toString();
            } finally {
                IOUtils. closeQuietly(inputStream);
                IOUtils.closeQuietly(writer);
            }

        } catch (SocketTimeoutException exe) {
            // LOG.error(exe.getMessage() + " error occurred while processing the request for path:- "+path, exe);
            /*sourceResponse.setResponseCode(Response.Status.SERVICE_UNAVAILABLE.getStatusCode());
            sourceResponse.setResponseAsString("The server cannot honour the request because it has been timed out");*/
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    private WebClient createWebClient(String address) {
        JacksonJsonProvider jacksonJaxbJsonProvider = new JacksonJsonProvider();
        jacksonJaxbJsonProvider.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WebClient webClient = WebClient.create(address, Collections.singletonList(jacksonJaxbJsonProvider));
        webClient.accept(MediaType.APPLICATION_JSON_TYPE);
        webClient.type(MediaType.APPLICATION_JSON_TYPE);
        return webClient;
    }
}
