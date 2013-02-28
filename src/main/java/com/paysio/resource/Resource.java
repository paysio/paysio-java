package com.paysio.resource;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.paysio.rest.Response;

public abstract class Resource {

    private String id;
    private Response response;
    private Long created;
    private Boolean livemode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    protected void copyProperties(Resource res) {
        Class<? extends Resource> clazz = res.getClass();

        if (this.getClass() != clazz) {
            throw new RuntimeException("Incompartible types!");
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] toPd = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : toPd) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    Method setter = propertyDescriptor.getWriteMethod();
                    Method getter = propertyDescriptor.getReadMethod();
                    setter.invoke(this, getter.invoke(res, (Object[]) null));
                }

            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }
}
