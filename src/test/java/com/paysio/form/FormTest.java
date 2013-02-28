package com.paysio.form;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.PaysioTest;
import com.paysio.exception.PaysioException;
import com.paysio.resource.Charge;
import com.paysio.resource.ChargeTest;

public class FormTest {
    
    static {
        Paysio.apiKey = PaysioTest.TEST_API_KEY;
        Paysio.apiPublishableKey = PaysioTest.TEST_API_KEY;
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRenderMapOfStringString() throws PaysioException {
        Charge charge = Charge.create(ChargeTest.getCreateParams());
        Form form = new Form("paysio");
        form.addParam("amount", charge.getAmount());
        form.addParam("charge_id", charge.getId());
        String formHtml = form.render();
        assertThat(formHtml, containsString("<form id=\"paysio\" action=\"\" method=\"POST\"></form>"));
        assertThat(formHtml, containsString("Paysio.setPublishableKey('" + PaysioTest.TEST_API_KEY + "');"));
    }

}
