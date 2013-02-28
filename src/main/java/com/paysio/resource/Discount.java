package com.paysio.resource;

public class Discount {

    private String id;
    private Long amount;
    private String currencyId;
    private NestedResource reason = new NestedResource();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Resource getReason() {
        return reason.getResource();
    }

    public void setReason(Resource reason) {
        this.reason.setResource(reason);
    }

}
