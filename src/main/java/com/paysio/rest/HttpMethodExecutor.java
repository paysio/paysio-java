package com.paysio.rest;

import static com.paysio.Paysio.apiKey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.paysio.exception.PaysioRuntimeException;
import com.paysio.util.Base64;

class HttpMethodExecutor {

    private enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    private static final String CHARSET = "UTF-8";

    public Response post(String url, String body) {
        return request(RequestMethod.POST, url, body);
    }

    public Response post(String url, String body, Map<String, Object> headers) {
        return request(RequestMethod.POST, url, body, headers);
    }

    public Response put(String url, String body) {
        return request(RequestMethod.PUT, url, body);
    }

    public Response put(String url, String body, Map<String, Object> headers) {
        return request(RequestMethod.PUT, url, body, headers);
    }

    public Response get(String url) {
        return request(RequestMethod.GET, url, null);
    }

    public Response get(String url, Map<String, Object> headers) {
        return request(RequestMethod.GET, url, null, headers);
    }

    public Response delete(String url) {
        return request(RequestMethod.DELETE, url, null);
    }

    public Response delete(String url, Map<String, Object> headers) {
        return request(RequestMethod.DELETE, url, null, headers);
    }

    private Response request(RequestMethod method, String url, String body) {
        return request(method, url, body, null);
    }

    private Response request(RequestMethod method, String url, String body,
            Map<String, Object> headers) {
        try {
            return processResponse(getConnection(method, url, body, headers));
        } catch (IOException e) {
            throw new PaysioRuntimeException("HTTP communication failure", e);
        }
    }

    private Response processResponse(HttpsURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        String message = conn.getResponseMessage();
        String content;
        String location = null;
        if (code >= 200 && code < 300) {
            content = readFromStream(conn.getInputStream());
            if (code == 201) {
                location = conn.getHeaderField("Location");
            }
        } else if (code >= 400) {
            content = readFromStream(conn.getErrorStream());
        } else {
            throw new PaysioRuntimeException(String.format("HTTP response is not supported: %d %s",
                    code, message));
        }
        return new Response(code, message, content, location);
    }

    private String readFromStream(InputStream in) throws IOException {
        return new Scanner(in, CHARSET).useDelimiter("\\A").next();
    }

    private HttpsURLConnection getConnection(RequestMethod method, String url, String body,
            Map<String, Object> headers) {
        HttpsURLConnection conn = getConnection(method, url);
        setRequestHeaders(conn, headers);
        writeRequestBody(conn, body);
        return conn;
    }

    private void writeRequestBody(HttpsURLConnection conn, String body) {
        if (body != null && !body.isEmpty()) {
            conn.setDoOutput(true);
            OutputStream out = null;
            try {
                out = conn.getOutputStream();
                out.write(body.getBytes(CHARSET));
            } catch (IOException e) {
                throw new PaysioRuntimeException(e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    private void setRequestHeaders(HttpsURLConnection conn, Map<String, Object> headers) {
        if (headers != null) {
            for (Entry<String, Object> header : headers.entrySet()) {
                String name = header.getKey();
                Object value = header.getValue();
                if (value != null) {
                    String strValue = null;
                    if (value instanceof Object[]) {
                        StringBuffer sb = new StringBuffer();
                        for (Object o : (Object[]) value) {
                            sb.append(',').append(o);
                        }
                        strValue = sb.substring(Math.min(sb.length(), 1));
                    } else {
                        strValue = value.toString();
                    }
                    conn.setRequestProperty(name, strValue);
                }
            }
        }
    }

    private HttpsURLConnection getConnection(RequestMethod method, String url) {

        if (apiKey == null || apiKey.isEmpty()) {
            throw new PaysioRuntimeException("No API key provided. " +
                    "(HINT: set your API key using 'Paysio.apiKey = <API-KEY>'. " +
                    "You can generate API keys from the Paysio web interface. " +
                    "See https://paysio.com/docs/api for details.");
        }

        try {
            URL serviceUrl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) serviceUrl
                    .openConnection();
            conn.setRequestMethod(method.toString());
            conn.setRequestProperty("Authorization",
                    "Basic " + Base64.encodeBytes((apiKey + ':').getBytes(CHARSET)));

            return conn;
        } catch (IOException e) {
            throw new PaysioRuntimeException(e);
        }
    }

}
