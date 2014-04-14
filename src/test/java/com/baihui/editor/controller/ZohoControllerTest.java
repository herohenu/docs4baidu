package com.baihui.editor.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 14-3-27.
 */
public class ZohoControllerTest {
    @Test
    public void testEdit() throws Exception {
        simple();
    }

    public void simple() throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("https://github.com");
        client.executeMethod(method);
        System.out.println(method.getStatusLine());
        System.out.println(method.getResponseBodyAsString());
        method.releaseConnection();
    }
}
