package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/wallets")
public class Wallet extends Resource {

    public static class WalletList extends ResourceList<Wallet> {}

    private static final RestClient<Wallet, WalletList> client = 
            new RestClient<Wallet, WalletList>(Wallet.class, WalletList.class);

    private String merchantId;
    private String type;
    private String account;
    private Map<String, Object> data;
    private String accountNumber;
    private String accountId;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public void update(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.update(getId(), params));
    }

    public void delete() throws PaysioException {
        this.copyProperties(client.delete(getId()));
    }

    public static Wallet create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Wallet retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static WalletList all() throws PaysioException {
        return client.list();
    }

    public static WalletList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Wallet parse(String json) {
        return client.parse(json);
    }

}
