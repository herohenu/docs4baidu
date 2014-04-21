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
import com.baihui.file.service.FileService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑器控制类
 *
 * @author xiayouxue
 * @date 2014/3/31
 */
@Controller
@RequestMapping(value = "")
public class EditorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FileService fileService;
    @Resource
    private PcsService pcsService;

    /**
     * 转至编辑器列表页
     */
    @RequestMapping(value = "/editor")
    public String list(Model model) throws UnsupportedEncodingException {
        Map<String, String> links = new LinkedHashMap<String, String>();
        links.put("zoho", "/editor/zoho");
        links.put("baihui", "/editor/baihui");
        model.addAttribute("links", links);
        return "/editor/index";
    }

    /**
     * 转至zoho编辑器页
     */
    @RequestMapping(value = "/editor/zoho")
    public String zoho(Model model) {
        String local = fileService.getBasePath() + "/file/temp.doc?method=download";
        String baidu = pcsService.getDownloadUrl("", "/apps/docs4baidu/temp.doc");
        model.addAttribute("basePath", fileService.getBasePath());
        model.addAttribute("downloadUrl", baidu);
        return "/editor/zoho";
    }


    /**
     * 编辑后保存文件
     */
    @RequestMapping(value = "/editor", params = "method=save")
    public void save(MultipartHttpServletRequest request, PrintWriter out) throws IOException {
        logger.info("编辑后保存文件");
        String name = request.getParameter("filename");
        logger.debug("\t原始文件名={}", name);
        List<MultipartFile> files = request.getFiles("content");
        logger.debug("\t文件数目:{}", files.size());
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            logger.debug("\tclientFile.name:{},client.originalFilename:{}", clientFile.getName(), clientFile.getOriginalFilename());
            File uploadFile = new File(fileService.getUploadPath() + "/" + name);
            if (uploadFile.exists() == false) {
                uploadFile.createNewFile(); //TODO 创建失败的具体处理
            }
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
        }
    }

}
