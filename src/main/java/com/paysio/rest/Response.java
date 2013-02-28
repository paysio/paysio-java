package com.paysio.rest;

public class Response {
    
    private int code;
    private String message;
    private String content;
    private String location;
    
    public Response(int code, String message, String conent) {
        this(code, message, conent, null);
    }

    public Response(int code, String message, String conent, String location) {
        this.code = code;
        this.message = message;
        this.content = conent;
        this.location = location;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }

    public String getLocation() {
        return location;
    }

}
