package com.paysio.rest;

public class Url {
    
	private String baseUrl;

	public Url(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getResourceSpecificURL(String id) {
		return String.format("%s/%s", baseUrl, id);
	}

    public String getResourceSpecificURL(String id, String method) {
        return String.format("%s/%s/%s", baseUrl, id, method);
    }

	public String getURL() {
		return baseUrl;
	}

    public String getURL(String query) {
        return getURL() + '?' + query;
    }

}
