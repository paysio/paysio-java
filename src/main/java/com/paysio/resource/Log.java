package com.paysio.resource;

import java.util.List;
import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/logs")
public class Log extends Resource {

    public static class LogList extends ResourceList<Log> {
    }

    private static final RestClient<Log, LogList> client = new RestClient<Log, LogList>(
            Log.class, LogList.class);

    private String merchantId;
    private String requestUrl;
    private String requestIp;
    private String requestMethod;
    private Map<String, String> requestGetParams;
    private Map<String, String> requestPostParams;
    private String requestQueryString;
    private String requestBody;
    private Integer responseStatus;
    private List<String> responseHeaders;
    private String responseBody;
    private Boolean rest;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getRequestGetParams() {
        return requestGetParams;
    }

    public void setRequestGetParams(Map<String, String> requestGetParams) {
        this.requestGetParams = requestGetParams;
    }

    public Map<String, String> getRequestPostParams() {
        return requestPostParams;
    }

    public void setRequestPostParams(Map<String, String> requestPostParams) {
        this.requestPostParams = requestPostParams;
    }

    public String getRequestQueryString() {
        return requestQueryString;
    }

    public void setRequestQueryString(String requestQueryString) {
        this.requestQueryString = requestQueryString;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(List<String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Boolean getRest() {
        return rest;
    }

    public void setRest(Boolean rest) {
        this.rest = rest;
    }

    public static Log retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static LogList all() throws PaysioException {
        return client.list();
    }

    public static LogList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Log parse(String json) {
        return client.parse(json);
    }

}