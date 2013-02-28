package com.paysio.resource;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NestedResourceDeserializer implements JsonDeserializer<NestedResource> {

	static Map<String, Class<? extends Resource>> objectMap = new HashMap<String, Class<? extends Resource>>();
    static {
        objectMap.put("charge", Charge.class);
        objectMap.put("coupon", Coupon.class);
        objectMap.put("customer", Customer.class);
        objectMap.put("reward", Reward.class);
        objectMap.put("wallet", Wallet.class);
    }
    
	public NestedResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		NestedResource eventData = new NestedResource();
		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("object").getAsString();
		Class<? extends Resource> cl = objectMap.get(type);
		Resource object = context.deserialize(json, cl);
		eventData.setResource(object);
		return eventData;
	}
}