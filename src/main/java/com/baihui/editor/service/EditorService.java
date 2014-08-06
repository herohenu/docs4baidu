package com.baihui.editor.service;

import com.baihui.editor.entity.Editor;
import com.baihui.editor.repository.EditorRepository;
import com.baihui.studio.service.ServiceException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

/**
 * 编辑器服务类
 *
 * @author xiayouxue
 * @date 2014/4/1
 */
@Service
public class EditorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${editor.baidu.openurl}")
    private String openUrl;

    @Value(value = "${editor.downloadUrl}")
    private String downloadUrl;

    @Value(value = "${editor.saveUrl}")
    private String saveUrl;

    @Value(value = "${editor.titlebar.id}")
    private String titlebarId;

    @Value(value = "${editor.com}")
    private String com;

    @Resource
    private EditorRepository editorRepository;

    public Editor findEditorByComExtension(String com, String extension) {
        Set<Editor> editors = editorRepository.getEditors();
        for (Editor editor : editors) {
            if (editor.getCom().equals(com) && editor.getFormats().contains(extension)) {
                return editor;
            }
        }
        throw new ServiceException(String.format("不支持的文件扩展名%s！", extension));
    }

    public Part[] buildParts(Editor editor) throws FileNotFoundException {
        return new Part[]{new StringPart("apikey", editor.getApikey()), new StringPart("output", "editor"),
                new StringPart("persistence", editor.getPersistence()), new StringPart("url", editor.getDownloadUrl()),
                new StringPart("filepath", editor.getFilePath(), "utf-8"),
                new StringPart("filename", editor.getFileName(), "utf-8"), new StringPart("format", editor.getFormat()),
                new StringPart("id", editor.getId()), new StringPart("saveurl", editor.getSaveUrl())};
    }

    public String open(Editor editor) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(editor.getServerUrl());
        Part[] parts = buildParts(editor);
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        String responseBody = IOUtils.toString(postMethod.getResponseBodyAsStream());
        logger.info("编辑器HTML代码:{}", responseBody);
        postMethod.releaseConnection();
        return responseBody;
    }

    public String getOpenUrl(String path) throws UnsupportedEncodingException {
        return String.format(openUrl, URLEncoder.encode(path, "utf-8"));
    }

    public String getSaveUrl(String token, String path) {
        return String.format(saveUrl, token, path);
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDownloadUrl(String filePath) {
        return String.format(downloadUrl, filePath);
    }


    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTitlebarId() {
        return titlebarId;
    }

    public void setTitlebarId(String titlebarId) {
        this.titlebarId = titlebarId;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
}
