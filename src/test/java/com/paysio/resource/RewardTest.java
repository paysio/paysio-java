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

public class RewardTest {

    private static final String REWARD_TITLE = "Клиентам, заплатившим больше 1000 руб., скидка 10%";
    private static final Integer REWARD_PERCENT_OFF = 10;
    private static final Long REWARD_PAYMENT_AMOUNT = 100000l;
    private static final String REWARD_CURRENCY = "rur";

    private static HashMap<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("title", REWARD_TITLE);
        params.put("percent_off", REWARD_PERCENT_OFF);
        params.put("payment_amount", REWARD_PAYMENT_AMOUNT);
        params.put("currency_id", REWARD_CURRENCY);
        return params;
    }
    
    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testCreate() throws PaysioException {
        Reward reward = Reward.create(getCreateParams());
        assertNotNull(reward);
        assertNotNull(reward.getId());
        assertNotNull(reward.getResponse());
        assertEquals(REWARD_TITLE, reward.getTitle());
        assertEquals(REWARD_PERCENT_OFF, reward.getPercentOff());
        assertEquals(REWARD_PAYMENT_AMOUNT, reward.getPaymentAmount());
        assertEquals(REWARD_CURRENCY, reward.getCurrencyId());
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Reward originalReward = Reward.create(getCreateParams());
        Reward retrievedReward = Reward.retrieve(originalReward.getId());
        assertEquals(originalReward.getId(), retrievedReward.getId());
        assertEquals(originalReward.getTitle(), retrievedReward.getTitle());
        assertEquals(originalReward.getCreated(), retrievedReward.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Reward.create(getCreateParams());
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Reward> rewards = Reward.all(listParams).getData();
        assertEquals(rewards.size(), 1);
    }

    @Test
    public void testUpdate() throws PaysioException {
        Reward reward = Reward.create(getCreateParams());
        String originalId = reward.getId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("max_uses", 100l);
        reward.update(params);
        assertEquals(originalId, reward.getId());
        assertEquals(Integer.valueOf(100), reward.getMaxUses());
    }

    @Test(expected=NotFoundException.class)
    public void testDelete() throws PaysioException {
        Reward reward = Reward.create(getCreateParams());
        reward.delete();
        Reward.retrieve(reward.getId());
    }

}
