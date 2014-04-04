package com.baihui.docs4baidu.editor.service.impl;

import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.repository.EditorRepository;
import com.baihui.docs4baidu.editor.service.EditorService;
import com.baihui.studio.service.ServiceException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * @author xiayouxue
 * @date 2014/3/31 19:23
 */
public class RemoteEditorService extends AbstractEditorService implements EditorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Part[] buildParts(Editor editor) throws FileNotFoundException {
        File file = editor.getContent();
        String extension = FilenameUtils.getExtension(file.getName());
        return new Part[]{new StringPart("apikey", editor.getApikey()), new StringPart("output", "editor"),
                new StringPart("persistence", editor.getPersistence()), new FilePart("content", file.getName(), file),
                new StringPart("filename", file.getName(), "UTF-8"), new StringPart("format", extension),
                new StringPart("id", "12345678"), new StringPart("saveurl", editor.getSaveUrl())};
    }

    public String open(Editor editor) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(editor.getServerUrl());
        Part[] parts = buildParts(editor);
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        String responseBody = postMethod.getResponseBodyAsString();
        logger.info("编辑器HTML代码:{}", responseBody);
        postMethod.releaseConnection();
        return responseBody;
    }

}
