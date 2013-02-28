package com.paysio.resource;

import java.util.Map;

import com.paysio.exception.PaysioException;
import com.paysio.resource.meta.ResourceConfig;
import com.paysio.rest.RestClient;

@ResourceConfig(url = "/v1/coupons")
public class Coupon extends Resource {

    public static class CouponList extends ResourceList<Coupon> {}

    private static final RestClient<Coupon, CouponList> client = new RestClient<Coupon, CouponList>(
            Coupon.class, CouponList.class);
    
    private String merchantId;
    private String code;
    private Integer percentOff;
    private Integer maxRedemptions;
    private Long maxAmount;
    private String currencyId;
    private Integer redeemBy;
    private Long redemptionsCount;
    private Long redeemed;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(Integer percentOff) {
        this.percentOff = percentOff;
    }

    public Integer getMaxRedemptions() {
        return maxRedemptions;
    }

    public void setMaxRedemptions(Integer maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
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

    public Integer getRedeemBy() {
        return redeemBy;
    }

    public void setRedeemBy(Integer redeemBy) {
        this.redeemBy = redeemBy;
    }

    public Long getRedemptionsCount() {
        return redemptionsCount;
    }

    public void setRedemptionsCount(Long redemptionsCount) {
        this.redemptionsCount = redemptionsCount;
    }

    public Long getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(Long redeemed) {
        this.redeemed = redeemed;
    }

    public void update(Map<String, Object> params) throws PaysioException {
        this.copyProperties(client.update(getId(), params));
    }

    public void delete() throws PaysioException {
        this.copyProperties(client.delete(getId()));
    }

    public static Coupon create(Map<String, Object> params) throws PaysioException {
        return client.create(params);
    }

    public static Coupon retrieve(String id) throws PaysioException {
        return client.getById(id);
    }

    public static CouponList all() throws PaysioException {
        return client.list();
    }

    public static CouponList all(Map<String, Object> params) throws PaysioException {
        return client.list(params);
    }

    public static Coupon check(String code) throws PaysioException {
        return client.getCustom(String.format("/code/%s/check", code));
    }

    public static Coupon parse(String json) {
        return client.parse(json);
    }

}
