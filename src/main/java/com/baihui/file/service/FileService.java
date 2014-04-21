package com.baihui.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 文件管理服务类
 *
 * @author xiayouxue
 * @date 2014/4/1
 */
@Service
public class FileService {

    @Value(value = "${application.uploadPath}")
    private String uploadPath;

    @Value(value = "${application.basePath}")
    private String basePath;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
