package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.BadRequestException;
import com.paysio.exception.PaysioException;
import com.paysio.resource.Payout;

public class PayoutTest {

    public static Map<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("amount", 1000);
        params.put("currency", "rur");
        params.put("payment_system_id", "test_phone_payout");
        params.put("description", "Test payout");
        params.put("wallet[account]", "79999999999");
        return params;
    }

    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testCreate() throws PaysioException {
        Payout payout = Payout.create(getCreateParams());
        assertNotNull(payout);
        assertNotNull(payout.getId());
        assertEquals(new Long(1000), payout.getAmount());
        assertEquals("79999999999", payout.getWallet().getAccount());
    }

    @Test(expected=BadRequestException.class)
    public void testCreateWithoutWallet() throws PaysioException {
        Map<String, Object> createParams = getCreateParams();
        createParams.remove("wallet[account]");
        Payout.create(createParams);
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Payout originalPayout = Payout.create(getCreateParams());
        Payout retrievedPayout = Payout.retrieve(originalPayout.getId());
        assertEquals(originalPayout.getId(), retrievedPayout.getId());
        assertEquals(originalPayout.getAmount(), retrievedPayout.getAmount());
        assertEquals(originalPayout.getCreated(), retrievedPayout.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Payout> payouts = Payout.all(listParams).getData();
        assertEquals(payouts.size(), 1);
    }

}
