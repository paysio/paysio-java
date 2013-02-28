package com.paysio.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paysio.Paysio;
import com.paysio.exception.BadRequestException;
import com.paysio.exception.ForbiddenException;
import com.paysio.exception.IntenalErrorException;
import com.paysio.exception.NotFoundException;
import com.paysio.exception.PaysioException;
import com.paysio.exception.PaysioRuntimeException;
import com.paysio.exception.UnauthorizedException;
import com.paysio.resource.NestedResource;
import com.paysio.resource.NestedResourceDeserializer;
import com.paysio.resource.Resource;
import com.paysio.resource.ResourceList;
import com.paysio.resource.meta.ResourceConfig;

public class RestClient<R extends Resource, L extends ResourceList<? extends Resource>> {

    private static final String CHARSET = "UTF-8";

    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(
            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(NestedResource.class, new NestedResourceDeserializer()).create();

    private final Url resourceUrl;
    private final Class<R> resourceClass;
    private final Class<L> resourceListClass;
    private final HttpMethodExecutor httpMethodExecutor;

    @SuppressWarnings("unused")
    private static class APIError {
        String type;
        String message;
        List<ParameterError> params;
    }

    private static class ErrorContainer {
        APIError error;
    }

    public RestClient(Class<R> resourceClass,
            Class<L> resourceListClass) {
        this(new Url(Paysio.API_BASE + resourceClass
                .getAnnotation(ResourceConfig.class).url()), resourceClass, resourceListClass,
                new HttpMethodExecutor());

    }

    RestClient(Url resourceUrl, Class<R> resourceClass,
            Class<L> resourceListClass, HttpMethodExecutor httpMethodExecutor) {
        this.resourceUrl = resourceUrl;
        this.resourceClass = resourceClass;
        this.resourceListClass = resourceListClass;
        this.httpMethodExecutor = httpMethodExecutor;

    }

    public R create(Map<String, Object> params) throws PaysioException {
        Map<String, Object> headers = new HashMap<String, Object>();
        if (params != null && params.get("ip") != null) {
            headers.put("X-Real-IP", params.get("ip"));
        }
        return processResponse(httpMethodExecutor.post(resourceUrl.getURL(), toQueryString(params), headers));
    }

    public L list() throws PaysioException {
        return list(null);
    }

    public L list(Map<String, Object> params) throws PaysioException {
        return processResponseAsList(httpMethodExecutor.get(resourceUrl
                .getURL(toQueryString(params))));
    }

    public R getById(String id) throws PaysioException {
        return processResponse(httpMethodExecutor.get(resourceUrl.getResourceSpecificURL(id)));
    }

    public R update(String id, Map<String, Object> params) throws PaysioException {
        return processResponse(httpMethodExecutor.put(resourceUrl.getResourceSpecificURL(id),
                toQueryString(params)));
    }

    public R execute(String id, String method) throws PaysioException {
        return execute(id, method, null);
    }

    public R execute(String id, String method, Map<String, Object> params) throws PaysioException {
        return processResponse(httpMethodExecutor.post(
                resourceUrl.getResourceSpecificURL(id, method), toQueryString(params)));
    }

    public R delete(String id) throws PaysioException {
        return processResponse(httpMethodExecutor.delete(resourceUrl.getResourceSpecificURL(id)));
    }

    public R getCustom(String ext) throws PaysioException {
        try {
            return processResponse(httpMethodExecutor.get(resourceUrl.getURL()
                    + URLEncoder.encode(ext, CHARSET)));
        } catch (UnsupportedEncodingException e) {
            throw new PaysioRuntimeException("Unable to encode url to " + CHARSET, e);
        }
    }

    private String toQueryString(Map<String, Object> params) {
        if (params == null) {
            return "";
        }

        StringBuffer query = new StringBuffer();
        for (Entry<String, Object> entry : params.entrySet()) {
            try {
                Object obj = entry.getValue();
                String value = obj instanceof Resource ? ((Resource) obj).getId() : obj.toString();
                query.append('&').append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(value, CHARSET));
            } catch (UnsupportedEncodingException e) {
                throw new PaysioRuntimeException("Unable to encode parameters to " + CHARSET, e);
            }
        }

        if (query.length() > 0) {
            query.deleteCharAt(0);
        }

        return query.toString();
    }

    private R processResponse(Response resp) throws PaysioException {
        int code = resp.getCode();
        if (code >= 200 && code < 300) {
            R resource = gson.fromJson(resp.getContent(), resourceClass);
            resource.setResponse(resp);
            return resource;
        } else {
            throw handlError(resp);
        }
    }

    private L processResponseAsList(Response resp) throws PaysioException {
        int code = resp.getCode();
        if (code >= 200 && code < 300) {
            return gson.fromJson(resp.getContent(), resourceListClass);
        } else {
            throw handlError(resp);
        }
    }

    private PaysioException handlError(Response resp) {
        String errorMessage = resp.getMessage();
        List<ParameterError> errorParams = null;
        ErrorContainer errorContainer = gson.fromJson(resp.getContent(), ErrorContainer.class);
        if (errorContainer != null && errorContainer.error != null) {
            errorMessage = errorContainer.error.message;
            errorParams = errorContainer.error.params;
        }
        switch (resp.getCode()) {
        case 400:
            return new BadRequestException(errorMessage, errorParams);
        case 401:
            return new UnauthorizedException(errorMessage);
        case 403:
            return new ForbiddenException(errorMessage);
        case 404:
            return new NotFoundException(errorMessage);
        case 500:
            return new IntenalErrorException(errorMessage);
        default:
            return new PaysioException(String.format("Unexpected response code: %d %s",
                    resp.getCode(), resp.getMessage()));
        }
    }

    public R parse(String json) {
        return gson.fromJson(json, resourceClass);
    }

}
