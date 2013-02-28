package com.paysio.form;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paysio.Paysio;
import com.paysio.rest.ParameterError;

public class Form {

    private static final int version = 1;
    
    public static final Gson gson = new GsonBuilder().
            serializeNulls().
            setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
            create();
        
    private String formId;

    private Map<String, Object> params = new HashMap<String, Object>();
    private Map<String, Object> values = new HashMap<String, Object>();
    private Map<String, Object> errors = new HashMap<String, Object>();

    private boolean withJquery = true;

    public Form(String formId) {
        this.formId = formId;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public void setErrors(List<ParameterError> paramErrors) {
        for (ParameterError error : paramErrors) {
            this.errors.put(error.getName(), error.getMessage());
        }
    }

    public void setWithJquery(boolean withJquery) {
        this.withJquery = withJquery;
    }

    public void render(OutputStream out) throws IOException {
        render(new HashMap<String, String>(), out);
    }

    public void render(Map<String, String> attributes, OutputStream out) throws IOException {
        out.write(_render(attributes).toString().getBytes());
    }

    public String render() {
        return render(new HashMap<String, String>());
    }

    public String render(Map<String, String> attributes) {
        return _render(attributes).toString();
    }

    private StringBuffer _render(Map<String, String> attributes) {
        StringBuffer sb = new StringBuffer();
        renderHead(sb);
        renderForm(sb, attributes);
        renderJS(sb);
        return sb;
    }

    private void renderHead(StringBuffer sb) {
        sb.append("<link href=\"").append(getStaticUrl())
                .append("/paysio.css\" type=\"text/css\" rel=\"styleSheet\" />\n");

        if (withJquery) {
            sb.append("<script src=\"https://yandex.st/jquery/1.8.1/jquery.min.js\"></script>\n");
        }

        sb.append("<script src=\"").append(getStaticUrl()).append("/paysio.js\"></script>\n");
    }

    private void renderForm(StringBuffer sb, Map<String, String> attributes) {
        Map<String, String> formAttributes = getDefaultAttributes();
        formAttributes.putAll(attributes);
        formAttributes.put("id", formId);
        
        sb.append("<form");
        for (Map.Entry<String, String> entry : formAttributes.entrySet()) {
            sb.append(' ').append(entry.getKey()).append("=\"").append(entry.getValue())
                    .append('"');
        }
        sb.append("></form>\n");
    }

    private void renderJS(StringBuffer sb) {
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("Paysio.setEndpoint('").append(getEndpoint()).append("');\n");
        sb.append("Paysio.setPublishableKey('").append(Paysio.apiPublishableKey).append("');\n");
        sb.append("Paysio.form.build($('#").append(formId).append("'), ").append(toJson(params));
        sb.append(", ").append(toJson(values));
        sb.append(", ").append(toJson(errors));
        sb.append(");\n");
        sb.append("</script>");
    }

    private String toJson(Map<String, Object> params) {
        return params != null ? gson.toJson(params) : "{}";
    }

    private String getEndpoint() {
        return String.format("%s/v%s", Paysio.API_BASE, version);
    }

    private String getStaticUrl() {
        return String.format("%s/static/v%s", Paysio.API_BASE.replace("api.", ""), version);
    }

    private static Map<String, String> getDefaultAttributes() {
        Map<String, String> defaults = new HashMap<String, String>();
        defaults.put("action", "");
        defaults.put("method", "POST");
        return defaults;
    }

}
