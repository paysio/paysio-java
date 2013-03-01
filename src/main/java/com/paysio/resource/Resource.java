package com.paysio.resource;

import java.lang.reflect.InvocationTargetException;

import com.paysio.exception.PaysioRuntimeException;
import com.paysio.rest.Response;
import com.paysio.util.BeanUtils;

public abstract class Resource {

    private String id;
    private Response response;
    private Long created;
    private Boolean livemode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    protected void copyProperties(Resource res) {
        try {
            BeanUtils.copyProperties(this, res);
        } catch (IllegalAccessException e) {
            throw new PaysioRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new PaysioRuntimeException(e);
        }
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }
}
