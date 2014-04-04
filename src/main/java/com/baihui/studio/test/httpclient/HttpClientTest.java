package com.baihui.studio.test.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xiayouxue
 * @date 2014/4/2 17:08
 */
public class HttpClientTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws IOException {
        String str = "李华";
        String encode = URLEncoder.encode(str, "utf-8");
        System.out.println(encode);
        System.out.println(URLDecoder.decode(encode, "utf-8"));
    }

    private static void test() throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost/docs4baidu/test/inputstream");
        postMethod.addParameter("name", "value");
        postMethod.addParameter("1", "1");
        httpClient.executeMethod(postMethod);
        String responseBody = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
    }
}
