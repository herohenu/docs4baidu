package com.baihui.docs4baidu.onlineoa.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

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
