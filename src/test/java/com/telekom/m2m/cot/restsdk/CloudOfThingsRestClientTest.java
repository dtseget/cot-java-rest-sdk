package com.telekom.m2m.cot.restsdk;


import com.telekom.m2m.cot.restsdk.util.CotSdkException;
import okhttp3.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by Patrick Steinert on 30.01.16.
 */
@PrepareForTest({OkHttpClient.class, CloudOfThingsRestClient.class, Response.class})
public class CloudOfThingsRestClientTest extends PowerMockTestCase {

    private final static String TEST_HOST = "https://test.m2m.telekom.com";
    private final static String TEST_USERNAME = "tester";
    private final static String TEST_PASSWORD = "anything-goes";

    @Test(expectedExceptions = CotSdkException.class)
    public void testDoRequestsWithIdResponseWithException() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenThrow(new RuntimeException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.doRequestWithIdResponse("", "", "", "");
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testDoRequestsWithIdResponseWithBadResponse() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(422);
        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.doRequestWithIdResponse("", "", "", "");
    }

    @Test
    public void testGetResponseWithError404() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(404);
        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);
        try {
            cloudOfThingsRestClient.getResponse("1234", "x", "y");
            fail();
        }catch(CotSdkException e) {
            assertEquals(e.getHttpStatus(), 404);
            assertEquals(e.getMessage(),"Error in request. id: 1234, api: x, accept: y HTTP status code:'404' (see https://http.cat/404)");
        }

    }

    @Test
    public void testGetResponseWithErrorNot404() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(405);
        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);
        try {
            cloudOfThingsRestClient.getResponse("1234", "x", "y");
            fail();
        }catch(CotSdkException e) {
            assertEquals(e.getHttpStatus(), 405);
            assertEquals(e.getMessage(),"Error in request. id: 1234, api: x, accept: y HTTP status code:'405' (see https://http.cat/405)");
        }

    }

    @Test
    public void testGetResponse() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);
        ResponseBody body = PowerMockito.mock(ResponseBody.class);
        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(true);
        PowerMockito.when(response.code()).thenReturn(200);
        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);
        PowerMockito.when(response.body()).thenReturn(body);
        PowerMockito.doReturn("").when(body,"string");


        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.getResponse("1234", "x", "y");


    }


    @Test(expectedExceptions = CotSdkException.class)
    public void testDoPutRequestWithException() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenThrow(new RuntimeException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.doPutRequest("", "", "");
    }

    @Test
    public void testConnection() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        CloudOfThingsRestClient cotRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testGetResponseWithException() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenThrow(new RuntimeException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.getResponse("", "", "");
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testDeleteWithException() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenThrow(new RuntimeException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.delete("", "");
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testDeleteWithIOException() throws Exception {

        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(404);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenThrow(new IOException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.delete("", "");
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testPostWithError() throws Exception {

        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);


        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenThrow(new IOException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.doPostRequest("", "", "", "");
    }

    @Test(expectedExceptions = CotSdkException.class)
    public void testDeleteWithReturningError() throws Exception {

        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(404);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.delete("", "");
    }

    @Test(expectedExceptions = { CotSdkException.class }, expectedExceptionsMessageRegExp = "Error in request:.*")
    public void testDeleteByWithException() throws Exception {
        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenThrow(new RuntimeException());

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.deleteBy("", "");
    }

    @Test(expectedExceptions = { CotSdkException.class }, expectedExceptionsMessageRegExp = "Error in delete by criteria.*")
    public void testDeleteByWithReturningError() throws Exception {

        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(false);
        PowerMockito.when(response.code()).thenReturn(404);

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.deleteBy("", "");
    }

    @Test
    public void testDeleteBy() throws Exception {

        OkHttpClient clientMock = PowerMockito.mock(OkHttpClient.class);
        Call call = PowerMockito.mock(Call.class);

        Response response = PowerMockito.mock(Response.class);

        PowerMockito.when(response.isSuccessful()).thenReturn(true);
        PowerMockito.when(response.code()).thenReturn(204); //204: no content

        PowerMockito.whenNew(OkHttpClient.class).withAnyArguments().thenReturn(clientMock);
        PowerMockito.when(clientMock.newCall(any(Request.class))).thenReturn(call);
        PowerMockito.when(call.execute()).thenReturn(response);

        CloudOfThingsRestClient cloudOfThingsRestClient = new CloudOfThingsRestClient(clientMock, TEST_HOST, TEST_USERNAME, TEST_PASSWORD);

        cloudOfThingsRestClient.deleteBy("", "");
    }
}
