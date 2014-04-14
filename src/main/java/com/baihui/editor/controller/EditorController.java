/**
 * @Title: ZohoController.java
 * @Package com.baihui.editor.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 16:07
 * @version V1.0
 */
package com.baihui.editor.controller;

import com.baihui.baidu.pcs.service.PcsService;
import com.baihui.editor.entity.Editor;
import com.baihui.editor.service.EditorService;
import com.baihui.file.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 编辑器控制类
 *
 * @author xiayouxue
 * @date 2014/3/31 20:08
 */
@Controller
@RequestMapping(value = "")
public class EditorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EditorService editorService;
    @Resource
    private FileService fileService;
    @Resource
    private PcsService pcsService;

    @RequestMapping(value = "/editor")
    public String list(Model model) throws UnsupportedEncodingException {
        Map<String, String> links = new LinkedHashMap<String, String>();
        links.put("zoho", "/editor/zoho");
        links.put("baihui", "/editor/baihui");
        model.addAttribute("links", links);
        return "/editor/index";
    }

    @RequestMapping(value = "/editor/zoho")
    public String zoho(Model model) {
        String local = fileService.getBasePath() + "/file/temp.doc?method=download";
        String baidu = pcsService.getDownloadUrl("", "/apps/docs4baidu/temp.doc");
        model.addAttribute("downloadUrl", baidu);
        return "/editor/zoho";
    }

    @RequestMapping(value = "/editor/baihui")
    public String baihui(Model model) {
        return "/editor/baihui";
    }

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
        Editor editor = editorService.findEditorByComExtension(com, extension);
        editor.setFileName(name);
        ObjectMapper mapper = new ObjectMapper();
        logger.info("editor:{}", mapper.writeValueAsString(editor));


        String fileUrl = String.format(editorService.getDownloadUrl(), URLEncoder.encode(name, "utf-8"));//TODO URL使用自身或者百度
        editor.setDownloadUrl(fileUrl);
        logger.info("fileUrl:{}", fileUrl);

        String content = editorService.open(editor);
        IOUtils.write(content, out);
    }

}
