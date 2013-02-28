package com.paysio.resource;

import static com.paysio.PaysioTest.TEST_API_KEY;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.paysio.Paysio;
import com.paysio.exception.PaysioException;

public class EventTest {

    static {
        Paysio.apiKey = TEST_API_KEY;
    }

    @Test
    public void testRetrieve() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        Event originalEvent = Event.all(listParams).getData().get(0);
        Event retrievedEvent = Event.retrieve(originalEvent.getId());
        assertEquals(originalEvent.getId(), retrievedEvent.getId());
        assertEquals(originalEvent.getCreated(), retrievedEvent.getCreated());
        assertNotNull(retrievedEvent.getData());
    }

    @Test 
    public void testAll() throws PaysioException {
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("count", 1);
        List<Event> events = Event.all(listParams).getData();
        assertEquals(events.size(), 1);
    }

}
