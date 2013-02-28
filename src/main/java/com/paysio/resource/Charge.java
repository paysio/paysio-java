package com.paysio.resource;

import java.util.List;
import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/charges")
public class Charge extends Resource {

    public static class ChargeList extends ResourceList<Charge> {
    }

    private static final RestClient<Charge, ChargeList> client = new RestClient<Charge, Charge.ChargeList>(
            Charge.class, ChargeList.class);

    private String merchantId;
    private String paymentSystemId;
    private String currencyId;
    private Long amount;
    private Long fee;
    private Long amountRefunded;
    private String description;
    private Wallet wallet;
    private Customer customer;
    private String status;
    private String statusCode;
    private Integer lifetime;
    private Map<String, Object> merchantData;
    private String orderId;
    private String ip;
    private List<PaymentSystemData> paymentSystemData;
    private Discount discount;
    private String successUrl;
    private String failureUrl;
    private String returnUrl;
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

    public Long getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Long amountRefunded) {
        this.amountRefunded = amountRefunded;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public Map<String, Object> getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(Map<String, Object> merchantData) {
        this.merchantData = merchantData;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<PaymentSystemData> getPaymentSystemData() {
        return paymentSystemData;
    }

    public void setPaymentSystemData(List<PaymentSystemData> paymentSystemData) {
        this.paymentSystemData = paymentSystemData;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public void update(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.update(getId(), params));
    }

    public void refund() throws PaysioException {
        this.copyProperties(client.execute(getId(), "refund"));
    }

    public void refund(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.execute(getId(), "refund", params));
    }

    public void invoice() throws PaysioException {
        this.copyProperties(client.execute(getId(), "invoice"));
    }
    
    public static Charge create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Charge retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static ChargeList all() throws PaysioException {
        return client.list();
    }

    public static ChargeList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Charge parse(String json) {
        return client.parse(json);
    }

}
