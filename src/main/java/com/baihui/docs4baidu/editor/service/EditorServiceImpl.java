package com.baihui.docs4baidu.editor.service;

import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.repository.EditorRepository;
import com.baihui.studio.service.ServiceException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * @author xiayouxue
 * @date 2014/4/1 20:44
 */
@Service
public class EditorServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${editor.baidu.authorizationcode.redirect_uri}")
    private String codeRedirectUri;

    @Value(value = "${editor.baidu.accesstoken.redirect_uri}")
    private String tokenRedirectUri;

    @Value(value = "${editor.fileUrl}")
    private String fileUrl;

    @Value(value = "${baidu.pcs.download}")
    private String downloadUrl;

    @Value(value = "${baidu.pcs.upload}")
    private String uploadUrl;

    @Resource
    private EditorRepository editorRepository;

    public Editor findEditorByComExtension(String com, String extension) {
        Set<Editor> editors = editorRepository.getEditors();
        for (Editor editor : editors) {
            if (editor.getCom().equals(com) && editor.getFormat().contains(extension)) {
                return editor;
            }
        }
        throw new ServiceException(String.format("没有找到公司%s、扩展名%s对应的编辑器配置！", com, extension));
    }

    public Part[] buildParts(Editor editor) throws FileNotFoundException {
        String extension = FilenameUtils.getExtension(editor.getFilename());
        return new Part[]{new StringPart("apikey", editor.getApikey()), new StringPart("output", "editor"),
                new StringPart("persistence", editor.getPersistence()),
                editor.getContent() == null ? new StringPart("url", editor.getFileUrl()) : new FilePart("content", editor.getFilename(), editor.getContent()),
                new StringPart("filename", editor.getFilename(),"utf-8"), new StringPart("format", extension),
                new StringPart("id", "12345678"), new StringPart("saveurl", editor.getSaveUrl())};
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

    public String getCodeRedirectUri() {
        return codeRedirectUri;
    }

    public void setCodeRedirectUri(String codeRedirectUri) {
        this.codeRedirectUri = codeRedirectUri;
    }

    public String getTokenRedirectUri() {
        return tokenRedirectUri;
    }

    public void setTokenRedirectUri(String tokenRedirectUri) {
        this.tokenRedirectUri = tokenRedirectUri;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }
}
