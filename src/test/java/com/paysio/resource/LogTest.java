package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.PaysioException;

public class LogTest {

    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        Log originalLog = Log.all(listParams).getData().get(0);
        Log retrievedLog = Log.retrieve(originalLog.getId());
        assertEquals(originalLog.getId(), retrievedLog.getId());
        assertEquals(originalLog.getCreated(), retrievedLog.getCreated());
    }

    @Test 
    public void testAll() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Log> logs = Log.all(listParams).getData();
        assertEquals(logs.size(), 1);
    }

}
