/**
 * @Title: ZohoController.java
 * @Package com.baihui.docs4baidu.editor.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 16:07
 * @version V1.0
 */
package com.baihui.docs4baidu.editor.controller;

import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.service.EditorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * 编辑器控制类
 *
 * @author xiayouxue
 * @version 1.0
 */
@Controller
@RequestMapping(value = "")
public class EditorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadFolderPath;

    @Value(value = "${application.basePath}")
    private String basePath;

    @Resource(name = "editorServiceImpl")
    private EditorServiceImpl editorService;

    /**
     * 打开编辑器
     * com:编辑器公司，zoho、baihui、baidu
     * way:打开方式，local、remote
     * name:文件名称
     */
    @RequestMapping(value = "/editor", params = "method=open")
    public void open(@RequestParam(value = "com") String com,
                     @RequestParam(value = "way", defaultValue = "local") String way,
                     @RequestParam(value = "name") String name,
                     PrintWriter out) throws IOException, CloneNotSupportedException {
        logger.info("打开编辑器，使用公司{}、方式{}，打开文件{}", com, way, name);//TODO 关于GET、POST请求对于编码的影响

        String extension = FilenameUtils.getExtension(name);
        Editor editor = editorService.findEditorByComExtension(com, extension).clone();
        editor.setWay(way);
        editor.setFilename(name);
        ObjectMapper mapper = new ObjectMapper();
        logger.info("editor:{}", mapper.writeValueAsString(editor));

        if (way.equals("local")) {
            File file = new File(uploadFolderPath + "/" + name);
            editor.setContent(file);
            logger.info("编辑器编辑文件，filePath:{}", file.getPath());
        } else if (way.equals("remote")) {
            String fileUrl = String.format(editorService.getFileUrl(), URLEncoder.encode(name,"utf-8"));//TODO URL使用自身或者百度
            editor.setFileUrl(fileUrl);
            logger.info("fileUrl:{}", fileUrl);
        }


        String content = editorService.open(editor);
        IOUtils.write(content, out);
    }

}
