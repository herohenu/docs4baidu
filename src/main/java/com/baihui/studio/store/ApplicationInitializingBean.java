package com.baihui.studio.store;

import com.baihui.studio.util.http.TrustAnyProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 应用初始化
 *
 * @author xiayouxue
 * @date 2014/4/14
 */
public class ApplicationInitializingBean implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${application.uploadPath}")
    private String uploadPath;


    @Override
    public void afterPropertiesSet() throws Exception {
        dealUploadFolder();
        registerProtocol();
    }

    /**
     * 处理上传文件夹
     */
    private void dealUploadFolder() {
        logger.info("处理上传文件夹，uploadPath={}", uploadPath);
        File uploadFolder = new File(uploadPath);
        logger.info("\t检查文件夹是否已存在");
        if (uploadFolder.exists() == false) {
            logger.warn("\t文件夹不存在！");
            boolean success = uploadFolder.mkdir();
            logger.info("\t创建上传文件夹！");
            if (success == false) {
                logger.error("创建上传文件夹失败！"); //TODO 失败的具体处理。停止服务器|让当前应用停止|屏蔽涉及上传文件部分内容
//                throw new Error("创建上传文件夹失败！");
            } else {
                logger.info("创建上传文件夹成功！");
                return;
            }
        }
        logger.info("\t文件夹已存在");
    }

    /**
     * 注册Https协议
     */
    private void registerProtocol() {
        logger.info("注册Https套接字工厂，信任所有证书");
        Protocol protocol = new Protocol("https", new TrustAnyProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", protocol);
    }
}
