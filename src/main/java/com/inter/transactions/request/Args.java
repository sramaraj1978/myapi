package com.inter.transactions.request;

import org.codehaus.jackson.annotate.JsonProperty;

public class Args implements GenericRequest {

    int uid;

    String token;

    @JsonProperty("api-token")
    String apiToken;

    @JsonProperty("json-strict-mode")
    boolean jsonStrictMode;

    @JsonProperty("json-verbose-response")
    boolean jsonVerboseResponse;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public boolean isJsonStrictMode() {
        return jsonStrictMode;
    }

    public void setJsonStrictMode(boolean jsonStrictMode) {
        this.jsonStrictMode = jsonStrictMode;
    }

    public boolean isJsonVerboseResponse() {
        return jsonVerboseResponse;
    }

    public void setJsonVerboseResponse(boolean jsonVerboseResponse) {
        this.jsonVerboseResponse = jsonVerboseResponse;
    }
}
