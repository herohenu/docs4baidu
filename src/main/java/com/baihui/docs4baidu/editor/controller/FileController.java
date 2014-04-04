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

import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件管理控制类
 *
 * @author xiayouxue
 * @version 1.0
 */
@Controller
@RequestMapping(value = "")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${application.basePath}")
    private String basePath;

    public String uploadFolderPath;

    @RequestMapping(value = "/file")
    public String list(Model model) {
        File uploadFolder = new File(uploadFolderPath);
        File[] files = uploadFolder.listFiles();
        model.addAttribute("basePath", basePath);
        model.addAttribute("files", files);
        return "/file/list";
    }

    @RequestMapping(value = "/file4editor")
    public String list4editor(Model model) {
        list(model);
        return "file/list4editor";
    }

    @RequestMapping(value = "/file", params = "method=upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        List<MultipartFile> files = request.getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            File uploadFile = new File(uploadFolderPath + "/" + clientFile.getOriginalFilename());
            if (uploadFile.exists() == false) {
                uploadFile.createNewFile(); //TODO 创建失败的具体处理
            }
            //文件已存在，则覆盖该文件
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
            logger.info("uploadFilePath:{}", uploadFile.getPath());
        }
        return "redirect:/file";
    }

    /**
     * 保存文件
     */
    @RequestMapping(value = "/file", params = "method=save")
    public void save(MultipartHttpServletRequest request, PrintWriter out) throws IOException {
        String name = request.getParameter("filename");
        name = URLDecoder.decode(name, "utf-8");
        logger.info("filename:{}", name);
        List<MultipartFile> files = request.getFiles("content");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            File uploadFile = new File(uploadFolderPath + "/" + name);
            logger.info("saveFilePath:{}", uploadFile.getPath());
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
        }
    }

    @RequestMapping(value = "/file/{name}", params = "method=delete")
    public String delete(@PathVariable("name") String name) {
        String filePath = uploadFolderPath + "/" + name;
        File file = new File(filePath);
        file.delete();  //TODO 删除失败的具体处理
        logger.info("deleteFilePath:{}", filePath);
        return "redirect:/file";
    }

    @RequestMapping(value = "/file/{name}", params = "method=download")
    public void download(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));//TODO 文件名乱码
        String filePath = uploadFolderPath + "/" + name;
        File file = new File(filePath);
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        logger.info("downloadFilePath:{}", filePath);
    }

}
