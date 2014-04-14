package com.baihui.studio.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;


public class ApplicationContextDataBean implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${application.uploadPath}")
    private String uploadPath;


    @Override
    public void afterPropertiesSet() throws Exception {
        dealUploadFolder();
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
                throw new Error("创建上传文件夹失败！");
            } else {
                logger.info("创建上传文件夹成功！");
                return;
            }
        }
        logger.info("文件夹已存在");
    }
}
