package com.baihui.studio.store;

import com.baihui.docs4baidu.editor.controller.BaiduyunEditorController;
import com.baihui.docs4baidu.editor.controller.EditorController;
import com.baihui.docs4baidu.editor.controller.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import javax.servlet.ServletContext;


public class ApplicationContextDataBean implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ServletContext servletContext = ApplicationContextDataListener.servletContext;


    @Resource
    private FileController fileController;

    @Resource
    private BaiduyunEditorController baiduyunController;

    @Resource
    private EditorController editorController;


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("手动注入属性");
        fileController.uploadFolderPath = (String) servletContext.getAttribute("UPLOADFOLDERPATH");
        baiduyunController.uploadFolderPath = fileController.uploadFolderPath;
        editorController.uploadFolderPath = fileController.uploadFolderPath;
        logger.info("   ZohoController.uploadFolderPath:{}", fileController.uploadFolderPath);
    }
}
