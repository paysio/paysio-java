package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.NotFoundException;
import com.paysio.exception.PaysioException;
import com.paysio.resource.Customer;

public class CustomerTest {

    private static final String CUSTOMER_NAME = "TestCustomer";
    private static final String CUSTOMER_EMAIL = "test@test.ru";
    private static final String CUSTOMER_DESCRIPTION = "Test customer";

    private static HashMap<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("email", CUSTOMER_EMAIL);
        params.put("description", CUSTOMER_DESCRIPTION);
        return params;
    }
    
    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testCreate() throws PaysioException {
        Customer customer = Customer.create(getCreateParams());
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(CUSTOMER_EMAIL, customer.getEmail());
        assertEquals(CUSTOMER_DESCRIPTION, customer.getDescription());
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Customer originalCustomer = Customer.create(getCreateParams());
        Customer retrievedCustomer = Customer.retrieve(originalCustomer.getId());
        assertEquals(originalCustomer.getId(), retrievedCustomer.getId());
        assertEquals(originalCustomer.getEmail(), retrievedCustomer.getEmail());
        assertEquals(originalCustomer.getCreated(), retrievedCustomer.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Customer.create(getCreateParams());
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Customer> customers = Customer.all(listParams).getData();
        assertEquals(customers.size(), 1);
    }

    @Test
    public void testUpdate() throws PaysioException {
        Customer customer = Customer.create(getCreateParams());
        assertEquals("", customer.getName());
        String originalId = customer.getId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", CUSTOMER_NAME);
        customer.update(params);
        assertEquals(originalId, customer.getId());
        assertEquals(CUSTOMER_NAME, customer.getName());
    }

    @Test(expected=NotFoundException.class)
    public void testDelete() throws PaysioException {
        Customer customer = Customer.create(getCreateParams());
        customer.delete();
        Customer.retrieve(customer.getId());
    }

}
