package com.paysio.resource;

import java.util.List;

public class ResourceList<T extends Resource> {
    private Integer count;
    private List<T> data;
    
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
}
