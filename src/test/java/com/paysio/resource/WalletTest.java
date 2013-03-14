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

public class WalletTest {

    public static final String WALLET_TYPE = "phone_number";
    public static final String WALLET_ACCOUNT = "79111111111";

    public static HashMap<String, Object> getCreateParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ip", "127.0.0.1");
        params.put("type", WALLET_TYPE);
        params.put("account", WALLET_ACCOUNT);
        return params;
    }
    
    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testCreate() throws PaysioException {
        Wallet wallet = Wallet.create(getCreateParams());
        assertNotNull(wallet);
        assertNotNull(wallet.getId());
        assertNotNull(wallet.getResponse());
        assertEquals(WALLET_ACCOUNT, wallet.getAccount());
        assertEquals(WALLET_TYPE, wallet.getType());
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Wallet originalWallet = Wallet.create(getCreateParams());
        Wallet retrievedWallet = Wallet.retrieve(originalWallet.getId());
        assertEquals(originalWallet.getId(), retrievedWallet.getId());
        assertEquals(originalWallet.getAccount(), retrievedWallet.getAccount());
        assertEquals(originalWallet.getCreated(), retrievedWallet.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Wallet.create(getCreateParams());
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Wallet> wallets = Wallet.all(listParams).getData();
        assertEquals(wallets.size(), 1);
    }

    @Test
    public void testUpdate() throws PaysioException {
        Wallet wallet = Wallet.create(getCreateParams());
        String originalId = wallet.getId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account_id", WALLET_ACCOUNT);
        wallet.update(params);
        assertEquals(originalId, wallet.getId());
        assertEquals(WALLET_ACCOUNT, wallet.getAccountId());
    }

    @Test(expected=NotFoundException.class)
    public void testDelete() throws PaysioException {
        Wallet wallet = Wallet.create(getCreateParams());
        wallet.delete();
        Wallet.retrieve(wallet.getId());
    }

}
