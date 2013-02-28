package com.paysio.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.paysio.exception.BadRequestException;
import com.paysio.exception.IntenalErrorException;
import com.paysio.exception.PaysioException;
import com.paysio.rest.TestResource.TestResourceList;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

    private static final String RESOURCE_ID = "hSDAFIdfdfhf8d9cc";
    private static final String RESOURCE_METHOD = "method";

    private static final String RESOURCE_URL = "https://someurl.com/v1/resource";
    private static final String SPECIFIC_RESOURCE_URL = String.format("%s/%s", RESOURCE_URL,
            RESOURCE_ID);
    private static final String SPECIFIC_RESOURCE_METHOD_URL = String.format("%s/%s",
            SPECIFIC_RESOURCE_URL, RESOURCE_METHOD);

    private static final String RESOURCE_JSON = "{" +
            " object: \"test_resource\"," +
            " id: \"" + RESOURCE_ID + "\"," +
            " string_field: \"string value\"," +
            " long_field: 100," +
            " integer_field: 101," +
            " boolean_field: true " +
            "}";

    private static final String RESOURCE_LIST_JSON = "{" +
            " object: \"list\"," +
            " count: 1," +
            " data: [ " + RESOURCE_JSON + " ]" +
            "}";

    private static final String ERROR_MESSAGE = "Invalid data parameters";
    private static final String ERROR_JSON = "{" +
            "  error: {" +
            "    params: [ " +
            "      {" +
            "        code: \"required\"," +
            "        message: \"Description cannot be blank.\"," +
            "        name: \"description\"" +
            "      } " +
            "    ]," +
            "    type: \"invalid_param_error\"," +
            "    message: \"" + ERROR_MESSAGE + "\"" +
            "  }" +
            "}";

    private static final Response RESOURCE_HTTP_RESP_200 =
            new Response(200, "OK", RESOURCE_JSON);
    private static final Response RESOURCE_HTTP_RESP_201 =
            new Response(201, "Created", RESOURCE_JSON, "<location>");
    private static final Response RESOURCE_LIST_HTTP_RESP_200 =
            new Response(200, "OK", RESOURCE_LIST_JSON);
    private static final Response RESOURCE_HTTP_RESP_400 =
            new Response(400, "Bad Request", ERROR_JSON);
    private static final Response RESOURCE_HTTP_RESP_500 =
            new Response(500, "Bad Request", "");

    private static final HashMap<String, Object> testResourceParams = getTestResourceParams();

    private static HashMap<String, Object> getTestResourceParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ip", "127.0.0.1");
        params.put("string_field", "string value");
        params.put("long_field", 100l);
        params.put("integer_field", 101);
        params.put("boolean_field", true);
        return params;
    }

    private RestClient<TestResource, TestResourceList> client;

    @Mock
    private HttpMethodExecutor httpMethodExecutor;

    @Before
    public void setUp() throws Exception {
        client = new RestClient<TestResource, TestResourceList>(new Url(RESOURCE_URL),
                TestResource.class,
                TestResourceList.class, httpMethodExecutor);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreate() throws PaysioException {
        when(
                httpMethodExecutor.post(eq(RESOURCE_URL), anyString(),
                        anyMapOf(String.class, Object.class))).thenReturn(
                RESOURCE_HTTP_RESP_201);
        TestResource testResource = client.create(testResourceParams);
        assertEquals(RESOURCE_ID, testResource.getId());
        assertEquals(RESOURCE_HTTP_RESP_201, testResource.getResponse());
    }

    @Test
    public void testList() throws PaysioException {
        when(httpMethodExecutor.get(anyString())).thenReturn(RESOURCE_LIST_HTTP_RESP_200);
        TestResourceList testResourceList = client.list();
        assertEquals(Integer.valueOf(1), testResourceList.getCount());
        assertEquals(RESOURCE_ID, testResourceList.getData().get(0).getId());
    }

    @Test
    public void testGetById() throws PaysioException {
        when(httpMethodExecutor.get(SPECIFIC_RESOURCE_URL)).thenReturn(RESOURCE_HTTP_RESP_200);
        TestResource testResource = client.getById(RESOURCE_ID);
        assertEquals(RESOURCE_ID, testResource.getId());
    }

    @Test
    public void testUpdate() throws PaysioException {
        when(httpMethodExecutor.put(eq(SPECIFIC_RESOURCE_URL), anyString())).thenReturn(
                RESOURCE_HTTP_RESP_200);
        TestResource testResource = client.update(RESOURCE_ID, testResourceParams);
        assertEquals(RESOURCE_ID, testResource.getId());
    }

    @Test
    public void testExecute() throws PaysioException {
        when(httpMethodExecutor.post(SPECIFIC_RESOURCE_METHOD_URL, "")).thenReturn(
                RESOURCE_HTTP_RESP_200);
        TestResource testResource = client.execute(RESOURCE_ID, RESOURCE_METHOD);
        assertEquals(RESOURCE_ID, testResource.getId());
    }

    @Test
    public void testDelete() throws PaysioException {
        when(httpMethodExecutor.delete(SPECIFIC_RESOURCE_URL)).thenReturn(RESOURCE_HTTP_RESP_200);
        TestResource testResource = client.delete(RESOURCE_ID);
        assertEquals(RESOURCE_ID, testResource.getId());
    }

    @Test(expected = BadRequestException.class)
    public void testBadRequest() throws PaysioException {
        when(httpMethodExecutor.put(SPECIFIC_RESOURCE_URL, "")).thenReturn(RESOURCE_HTTP_RESP_400);
        try {
            client.update(RESOURCE_ID, null);
        } catch (BadRequestException e) {
            assertEquals(ERROR_MESSAGE, e.getMessage());
            assertEquals(1, e.getParameterErrors().size());
            throw e;
        }
    }

    @Test(expected = IntenalErrorException.class)
    public void testInternalError() throws PaysioException {
        when(httpMethodExecutor.get(SPECIFIC_RESOURCE_URL)).thenReturn(RESOURCE_HTTP_RESP_500);
        client.getById(RESOURCE_ID);
    }

    @Test
    public void testParse() throws PaysioException {
        TestResource testResource = client.parse(RESOURCE_JSON);
        assertEquals(RESOURCE_ID, testResource.getId());
        assertEquals(Boolean.valueOf(true), testResource.getBooleanField());
        assertEquals(Long.valueOf(100), testResource.getLongField());
        assertEquals(Integer.valueOf(101), testResource.getIntegerField());
    }

}
