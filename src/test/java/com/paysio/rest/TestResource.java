package com.paysio.rest;

import com.paysio.resource.Resource;
import com.paysio.resource.ResourceList;

class TestResource extends Resource {
    
    static class TestResourceList extends ResourceList<TestResource> {}
    
    private String stringField;
    private Long longField;
    private Integer integerField;
    private Boolean booleanField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(Integer integerField) {
        this.integerField = integerField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

}