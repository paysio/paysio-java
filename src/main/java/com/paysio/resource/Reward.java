package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/rewards")
public class Reward extends Resource {

    public static class RewardList extends ResourceList<Reward> {}

    private static final RestClient<Reward, RewardList> client = new RestClient<Reward, RewardList>(
            Reward.class, RewardList.class);

    private String merchantId;
    private String title;
    private Integer percentOff;
    private Integer maxUses;
    private Long maxAmount;
    private String currencyId;
    private Long paymentAmount;
    private Long paymentFromDate;
    private Long paymentToDate;
    private Long expiresAt;
    private Long usesCount;
    private Long used;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(Integer percentOff) {
        this.percentOff = percentOff;
    }

    public Integer getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUsers) {
        this.maxUses = maxUsers;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getPaymentFromDate() {
        return paymentFromDate;
    }

    public void setPaymentFromDate(Long paymentFromDate) {
        this.paymentFromDate = paymentFromDate;
    }

    public Long getPaymentToDate() {
        return paymentToDate;
    }

    public void setPaymentToDate(Long paymentToDate) {
        this.paymentToDate = paymentToDate;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getUsesCount() {
        return usesCount;
    }

    public void setUsesCount(Long usesCount) {
        this.usesCount = usesCount;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public void update(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.update(getId(), params));
    }

    public void delete() throws PaysioException {
        this.copyProperties(client.delete(getId()));
    }

    public static Reward create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Reward retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static RewardList all() throws PaysioException {
        return client.list();
    }

    public static RewardList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Reward parse(String json) {
        return client.parse(json);
    }

}
