package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.NotFoundException;
import com.paysio.exception.PaysioException;

public class CouponTest {

    private static final Integer COUPON_PERCENT_OFF = 10;

    private HashMap<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ip", "127.0.0.1");
        params.put("code", UUID.randomUUID().toString());
        params.put("percent_off", COUPON_PERCENT_OFF);
        return params;
    }
    
    static {
        Paysio.apiKey = TEST_API_KEY;
    }
    
    @Test
    public void testCreate() throws PaysioException {
        Coupon coupon = Coupon.create(getCreateParams());
        assertNotNull(coupon);
        assertNotNull(coupon.getId());
        assertNotNull(coupon.getResponse());
        assertEquals(COUPON_PERCENT_OFF, coupon.getPercentOff());
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Coupon originalCoupon = Coupon.create(getCreateParams());
        Coupon retrievedCoupon = Coupon.retrieve(originalCoupon.getId());
        assertEquals(originalCoupon.getId(), retrievedCoupon.getId());
        assertEquals(originalCoupon.getCode(), retrievedCoupon.getCode());
        assertEquals(originalCoupon.getCreated(), retrievedCoupon.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Coupon.create(getCreateParams());
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Coupon> Coupons = Coupon.all(listParams).getData();
        assertEquals(Coupons.size(), 1);
    }

    @Test
    public void testUpdate() throws PaysioException {
        Coupon coupon = Coupon.create(getCreateParams());
        String originalId = coupon.getId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("max_redemptions", 1000l);
        coupon.update(params);
        assertEquals(originalId, coupon.getId());
        assertEquals(Integer.valueOf(1000), coupon.getMaxRedemptions());
    }

    @Test(expected=NotFoundException.class)
    public void testDelete() throws PaysioException {
        Coupon coupon = Coupon.create(getCreateParams());
        coupon.delete();
        Coupon.retrieve(coupon.getId());
    }

    @Test
    public void testCheck() throws PaysioException {
        Coupon originalCoupon = Coupon.create(getCreateParams());
        Coupon checkedCoupon = Coupon.check(originalCoupon.getCode());
        assertEquals(originalCoupon.getId(), checkedCoupon.getId());
    }

    @Test(expected=NotFoundException.class)
    public void testCheckFailed() throws PaysioException {
        Coupon.check("wrong-coupon-code-" + UUID.randomUUID());
    }
}
