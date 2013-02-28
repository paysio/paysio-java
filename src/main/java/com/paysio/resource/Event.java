package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/events")
public class Event extends Resource {

    public static class EventList extends ResourceList<Event> {
    }

    private static final RestClient<Event, EventList> client = new RestClient<Event, EventList>(
            Event.class, EventList.class);

    private String merchantId;
    private String type;
    private NestedResource data = new NestedResource();

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Resource getData() {
        return data.getResource();
    }

    public void setData(Resource data) {
        this.data.setResource(data);
    }

    public static Event retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static EventList all() throws PaysioException {
        return client.list();
    }

    public static EventList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Event parse(String json) {
        return client.parse(json);
    }

}