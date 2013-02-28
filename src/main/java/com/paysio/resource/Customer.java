package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/customers")
public class Customer extends Resource {
    
    public static class CustomerList extends ResourceList<Customer> {}

    private static final RestClient<Customer, CustomerList> client = new RestClient<Customer, CustomerList>(
            Customer.class, CustomerList.class);

    private String merchantId;
    private String email;
    private String phoneNumber;
    private String name;
    private String description;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void update(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.update(getId(), params));
    }

    public void delete() throws PaysioException {
        this.copyProperties(client.delete(getId()));
    }

    public static Customer create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Customer retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static CustomerList all() throws PaysioException {
        return client.list();
    }

    public static CustomerList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Customer parse(String json) {
        return client.parse(json);
    }

}
