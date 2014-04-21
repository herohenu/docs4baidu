package com.baihui.baidu.pcs.service;

import com.baihui.studio.util.http.TrustAnyProtocolSocketFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 百度开放认证
 *
 * @author xiayouxue
 * @date 2014/4/2 16:23
 */
@Service
public class PcsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${baidu.pcs.files}")
    private String files;

    @Value(value = "${baidu.pcs.trustpath}")
    private String trustpath;

    @Value(value = "${baidu.pcs.maxtotalconnections}")
    private Integer maxTotalConnections;

    @Value(value = "${baidu.pcs.upload}")
    private String upload;

    @Value(value = "${baidu.pcs.download}")
    private String download;

    /**
     * 上传文件至百度服务器
     */
    public String upload(String token, String path, Part[] parts) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        String uploadUrl = getUploadUrl(token, path);
        logger.debug("\t上传文件至百度服务器，URL={}", uploadUrl);

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        HttpClient httpClient = new HttpClient(connectionManager);
        PostMethod postMethod = new PostMethod(uploadUrl);
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        String result = IOUtils.toString(postMethod.getResponseBodyAsStream());
        postMethod.releaseConnection();
        return result;
    }


    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDownloadUrl(String token, String path) {
        return String.format(download, token, path);
    }

    public String getUploadUrl(String token, String path) {
        return String.format(upload, token, path);
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getTrustpath() {
        return trustpath;
    }

    public void setTrustpath(String trustpath) {
        this.trustpath = trustpath;
    }
}
