/**
 * @Title: ApplicationContextDataListener.java
 * @Package com.baihui.studio.store
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-27 18:27
 * @version V1.0
 */
package com.baihui.studio.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * 应用级数据存储监听器
 *
 * @author xiayouxue
 * @version version 1.0
 * @ClassName:com.baihui.studio.store.ApplicationContextListener.java
 * @company 北京百会纵横科技有限公司</p>
 * @copyright 本文件归属 北京百会纵横科技有限公司</p>
 * @since(该版本支持的 JDK 版本) ： 1.6
 * @date 2014-03-27 18:27
 * @modify version 1.0
 */
public class ApplicationContextDataListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        setUploadFolderPath(servletContext);
    }

    /**
     * 设置上传文件夹路径
     *
     * @param servletContext ServletContext
     * @return 上传文件夹路径
     */
    private void setUploadFolderPath(ServletContext servletContext) {
        String uploadFolderPath = servletContext.getRealPath("/") + "upload";//TODO getRealPath用法

        //文件存在性判断
        File uploadFolder = new File(uploadFolderPath);
        if (uploadFolder.exists() == false) {
            logger.warn("上传文件夹“{}”不存在！", uploadFolderPath);
            boolean success = uploadFolder.mkdir();
            logger.info("创建上传文件夹“{}”！", uploadFolderPath);
            if (success == false) {
                logger.warn("创建上传文件夹“{}”失败！", uploadFolderPath); //TODO 失败的具体处理。停止服务器|让当前应用停止|屏蔽涉及上传文件部分内容
                return;
            } else {
                logger.info("创建上传文件夹“{}”成功！", uploadFolderPath);
            }
        }

        servletContext.setAttribute("UPLOADFOLDERPATH", uploadFolderPath);
        logger.info("设置上传文件夹路径：UPLOADFOLDERPATH->{}", uploadFolderPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
