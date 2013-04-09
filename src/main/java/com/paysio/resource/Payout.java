package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/payouts")
public class Payout extends Resource {

    public static class PayoutList extends ResourceList<Payout> {}

    private static final RestClient<Payout, PayoutList> client = new RestClient<Payout, PayoutList>(
            Payout.class, PayoutList.class);
    
    private String merchantId;
    private String paymentSystemId;
    private String currencyId;
    private Long amount;
    private Long fee;
    private String description;
    private Wallet wallet;
    private String status;
    private String statusCode;
    private Long updated;
    
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentSystemId() {
        return paymentSystemId;
    }

    public void setPaymentSystemId(String paymentSystemId) {
        this.paymentSystemId = paymentSystemId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public static Payout create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Payout retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static PayoutList all() throws PaysioException {
        return client.list();
    }

    public static PayoutList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Payout parse(String json) {
        return client.parse(json);
    }

}
