package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.PaysioException;
import com.paysio.resource.Charge;

public class ChargeTest {

    public static Map<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ip", "127.0.0.1");
        params.put("amount", 100);
        params.put("currency", "rur");
        params.put("payment_system_id", "test");
        params.put("description", "Test charge");
        return params;
    }

    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testCreate() throws PaysioException {
        Charge charge = Charge.create(getCreateParams());
        assertNotNull(charge);
        assertNotNull(charge.getId());
        assertEquals(new Long(100), charge.getAmount());
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Charge originalCharge = Charge.create(getCreateParams());
        Charge retrievedCharge = Charge.retrieve(originalCharge.getId());
        assertEquals(originalCharge.getId(), retrievedCharge.getId());
        assertEquals(originalCharge.getAmount(), retrievedCharge.getAmount());
        assertEquals(originalCharge.getCreated(), retrievedCharge.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Charge> charges = Charge.all(listParams).getData();
        assertEquals(charges.size(), 1);
    }

    @Test
    public void testUpdate() throws PaysioException {
        Map<String, Object> testChargeParams = getCreateParams();
        testChargeParams.remove("payment_system_id");
        Charge charge = Charge.create(testChargeParams);
        assertEquals(null, charge.getPaymentSystemId());
        String originalId = charge.getId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("payment_system_id", "test");
        charge.update(params);
        assertEquals(originalId, charge.getId());
        assertEquals("test", charge.getPaymentSystemId());
    }

    @Test
    public void testRefund() throws PaysioException {
        Map<String, Object> createParams = getCreateParams();
        createParams.put("payment_system_id", "test_success");
        createParams.put("wallet[account]", WalletTest.WALLET_ACCOUNT);
        Charge charge = Charge.create(createParams);
        charge.refund();
        assertEquals(charge.getAmount(), charge.getAmountRefunded());
    }

    @Test
    public void testInvoice() throws PaysioException {
        Charge charge = Charge.create(getCreateParams());
        charge.invoice();
    }

}
