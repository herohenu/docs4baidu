package com.baihui.baidu.pcs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 百度开放认证
 *
 * @author xiayouxue
 * @date 2014/4/2 16:23
 */
@Service
public class PcsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${baidu.pcs.upload}")
    private String upload;

    @Value(value = "${baidu.pcs.download}")
    private String download;

    public String getDownloadUrl(String token, String path) {
        return String.format(download, token, path);
    }


}
