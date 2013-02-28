package com.paysio;

import java.util.HashMap;
import java.util.Map;

import com.paysio.exception.BadRequestException;
import com.paysio.exception.PaysioException;
import com.paysio.form.Form;
import com.paysio.resource.Charge;
import com.paysio.resource.Event;
import com.paysio.resource.Wallet;

public class Examples {
    
    public static Form formCreate(String paymentSystemId, String walletJson) throws PaysioException {
        Paysio.apiKey = "HZClZur5OW3BYimWSydQNsArbph2L7IRo0ql8HK";
        long amount = 3999l; // сумма в копейках 39.99 руб
        
        Form form = new Form("paysio");
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("amount", amount);
        form.setParams(formParams);
        
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "rur");
        chargeParams.put("payment_system_id", paymentSystemId);
        chargeParams.put("description", "Test charge");
        chargeParams.put("success_url", "#SUCCESS_URL#");
        chargeParams.put("failure_url", "#FAILURE_URL#");
        chargeParams.put("return_url", "#RETURN_URL#");
        
        if (walletJson != null) {
            Wallet wallet = Wallet.parse(walletJson);
            chargeParams.put("wallet", wallet);
        }
        
        try {
            Charge charge = Charge.create(chargeParams);
            form.addParam("charge_id", charge.getId());
        } catch (BadRequestException e) {
            form.setErrors(e.getParameterErrors());
        }
        
        return form;
    }
    
    public static String formRender(Form form) {
        Paysio.apiPublishableKey = "pk_7MrhSVEjYq8F1PKEqhAj192fZUV8Ooitl4GQBkL";
        
        return form.render(); // отобразить форму или результат платежа в зависимости от параметров
    }
    
    public static void redirect() throws PaysioException {
        Paysio.apiKey = "HZClZur5OW3BYimWSydQNsArbph2L7IRo0ql8HK";
        long amount = 3999l; // сумма в копейках 39.99 руб
        
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "rur");
        chargeParams.put("description", "Test charge");
        chargeParams.put("success_url", "#SUCCESS_URL#");
        chargeParams.put("failure_url", "#FAILURE_URL#");
        chargeParams.put("return_url", "#RETURN_URL#");
        chargeParams.put("ip", "#CLIENT_IP#");
        
        Charge charge = Charge.create(chargeParams);
                
        if (charge.getResponse().getLocation() != null) {
            // redirect to the location
        }
    }
    
    public static void webhook(String webhookRequestBody) throws PaysioException {
        Event event = Event.parse(webhookRequestBody);
        
        event = Event.retrieve(event.getId());
        if (event.getData() instanceof Charge) {
            String type = event.getType();
            if ("charge.success".equals(type)) {
                // logic for charge success
            } else if ("charge.failure".equals(type)) {
                // logic for charge failure
            } else if ("charge.refund".equals(type)) {
                // logic for charge refund
            }
        }
    }
}
