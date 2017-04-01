package com.inter.transactions.service;

import com.google.gson.Gson;
import com.inter.transactions.request.GenericRequest;
import com.inter.transactions.request.TransactionsRequest;
import com.inter.transactions.response.*;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.HttpMethod;


import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by suresh
 */
@Service("transactionsDetailsService")
@Path("profile/transactionsDetailsService")
@Api(value = "profile/transactionsDetailsService", basePath = "/services/provider",
        description = "Transactions Details Service with Average")
public class TransactionsDetailsService extends TransactionsBaseService {

    private String endpoint;

    private String apiURL;

    public TransactionsDetailsService(String endpoint, String apiURL) {
        this.endpoint = endpoint;
        this.apiURL = apiURL;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            httpMethod = "POST",
            value = "Post operation  - Gets transactions with monthly breakup and Overall Average Result"
    )
    public Response getMonthlyAverageTransactions(TransactionsRequest transactionsRequest) throws IOException, ParseException {
        Response response = makeServiceCall(transactionsRequest, "POST");
        TransactionsResponse transactionsResponse = new Gson().fromJson((String) response.getEntity(), TransactionsResponse.class);
        TransactionsResult transactionsResult = prepareStatement(transactionsResponse, false);
        return Response.status(Response.Status.OK).entity(transactionsResult).build();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }
}
