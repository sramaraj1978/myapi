package com.inter.transactions.service;

import com.google.gson.Gson;
import com.inter.transactions.request.GenericRequest;
import com.inter.transactions.request.TransactionsRequest;
import com.inter.transactions.response.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
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
 * Created by suresh
 */
@Service("transactionsDetailsFilteredService")
@Path("profile/transactionsDetailsFilteredService")
@Api(value = "profile/transactionsDetailsFilteredService", basePath = "/services/provider",
        description = "Transactions Details Donut Filtered Service")
public class TransactionsDetailsFilteredService extends TransactionsBaseService {

    private String endpoint;

    private String apiURL;

    public TransactionsDetailsFilteredService(String endpoint, String apiURL) {
        this.endpoint = endpoint;
        this.apiURL = apiURL;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            httpMethod = "POST",
            value = "Post operation - Gets transactions with Donuts Spending Filtered"
    )
    public Response getDonutFilteredMonthlyAverageTransactions(TransactionsRequest transactionsRequest) throws IOException, ParseException {
        Response response = makeServiceCall(transactionsRequest, "POST");
        TransactionsResponse transactionsResponse = new Gson().fromJson((String) response.getEntity(), TransactionsResponse.class);
        TransactionsResult transactionsResult = prepareStatement(transactionsResponse, true);
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
