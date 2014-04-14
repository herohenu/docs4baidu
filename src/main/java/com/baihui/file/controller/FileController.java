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
package com.baihui.file.controller;

import com.baihui.file.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 文件管理控制类
 *
 * @author xiayouxue
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FileService fileService;

    /**
     * 显示文件列表
     */
    @RequestMapping(value = "", method = {RequestMethod.GET})
    public String list(Model model) {
        File uploadFolder = new File(fileService.getUploadPath());
        File[] files = uploadFolder.listFiles();
        logger.info("显示文件列表，files.length={}", files.length);
        model.addAttribute("files", files);
        return "/file/list";
    }

    /**
     * 上传文件
     */
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public String upload(MultipartHttpServletRequest request) throws IOException {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        logger.info("上传文件，files.length={}", fileMap.size());
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            MultipartFile clientFile = entry.getValue();
            File uploadFile = new File(fileService.getUploadPath() + "/" + clientFile.getOriginalFilename());
            if (uploadFile.exists() == false) {
                uploadFile.createNewFile(); //TODO 创建失败的具体处理
            }
            //文件已存在，则覆盖该文件
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
            logger.info("\t表单名称={}，文件路径={}", entry.getKey(), uploadFile.getPath());
        }
        return "redirect:/file";
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "/{name}", method = {RequestMethod.GET}, params = {"method=download"})
    public void download(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));//TODO 文件名乱码
        String filePath = fileService.getUploadPath() + "/" + name;
        logger.info("下载文件，path={}", filePath);
        File file = new File(filePath);
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    /**
     * 编辑文件
     * //TODO 编辑文件未实现
     */
    @RequestMapping(value = "/{name}", method = {RequestMethod.POST}, params = {"method=edit"})
    public String edit(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        String filePath = fileService.getUploadPath() + "/" + name;
        logger.info("编辑文件，path={}", filePath);
        return "redirect:/file";
    }

    /**
     * 删除文件
     */
    @RequestMapping(value = "/{name}", method = {RequestMethod.GET}, params = {"method=delete"})
    public String delete(@PathVariable("name") String name) {
        String filePath = fileService.getUploadPath() + "/" + name;
        logger.info("删除文件，path={}", filePath);
        File file = new File(filePath);
        file.delete();  //TODO 删除失败的具体处理
        return "redirect:/file";
    }

}
