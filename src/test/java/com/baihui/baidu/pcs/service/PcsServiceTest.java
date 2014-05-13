package com.baihui.baidu.pcs.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiayouxue
 * @date 2014/4/18
 */
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PcsServiceTest extends AbstractJUnit4SpringContextTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PcsService pcsService;

    @Test
    public void testUpload() throws Exception {
        HttpClient httpclient = new HttpClient();
        httpclient.getHostConfiguration().setProxy("myproxyhost", 8080);
        httpclient.getState().setProxyCredentials("my-proxy-realm", " myproxyhost",
                new UsernamePasswordCredentials("my-proxy-username", "my-proxy-password"));
        GetMethod httpget = new GetMethod("https://www.verisign.com/");
        httpclient.executeMethod(httpget);
        System.out.println(httpget.getStatusLine().toString());
    }

    private void test2() throws IOException {
        HttpClient httpclient = new HttpClient();
        GetMethod httpget = new GetMethod("https://www.verisign.com/");
        httpclient.executeMethod(httpget);
        System.out.println(httpget.getStatusLine().toString());
    }

    private void test1() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        Part[] parts = new Part[1];
        File file = new File("E:\\project\\company\\docs4baidu-2.0\\target\\docs4baidu-1.0-SNAPSHOT\\upload\\temp.xls");
//        parts[0] = new FilePart("temp.xls", file);
        parts[0] = new FilePart("file", new ByteArrayPartSource("sdfdf.xml", FileUtils.readFileToByteArray(file)));
        String token = "21.db07a4fc5aa6ab761655d7d64a1a6152.2592000.1400651317.1293204608-2104893";
//        System.setProperty("javax.net.ssl.trustStore", pcsService.getTrustpath());
        String result = pcsService.upload(token, "/apps/docs4baidu/temp.xls", parts);
        logger.info("result:{}", result);
    }
}
