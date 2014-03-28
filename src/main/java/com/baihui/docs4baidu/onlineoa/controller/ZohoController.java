/**
 * @Title: ZohoController.java
 * @Package com.baihui.docs4baidu.onlineoa.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 16:07
 * @version V1.0
 */
package com.baihui.docs4baidu.onlineoa.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zoho在线办公
 * 编辑文件
 * 1.上传文件
 * 1.1)本地选择文件，表单提交
 * 1.2)文件列表选择文件，服务器上传
 * 2.转至编辑界面
 * 3.编辑文件
 * 4.保存文件
 * <p/>
 * 创建文件
 * 1.转至编辑界面
 * 2.编辑文件
 * 3.保存文件
 * <p/>
 * 辅助功能
 * 1.下载
 * 2.删除
 *
 * @author xiayouxue
 * @version version 1.0
 * @ClassName:com.baihui.docs4baidu.onlineoa.controller.ZohoController.java
 * @company 北京百会纵横科技有限公司</p>
 * @copyright 本文件归属 北京百会纵横科技有限公司</p>
 * @since(该版本支持的 JDK 版本) ： 1.6
 * @date 2014-03-25 16:07
 * @modify version 1.0
 */
@Controller
@RequestMapping(value = "/onlineoa/zoho")
public class ZohoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${application.basePath}")
    private String basePath;

    public String uploadFolderPath;

    @RequestMapping(value = "/file")
    public String fileList(Model model) {
        File uploadFolder = new File(uploadFolderPath);
        File[] files = uploadFolder.listFiles();
        model.addAttribute("basePath", basePath);
        model.addAttribute("files", files);
        return "/onlineoa/zoho/fileList";
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
        return "redirect:/onlineoa/zoho/file";
    }

    @RequestMapping(value = "/file/{name}", params = "method=delete")
    public String delete(@PathVariable("name") String name) {
        String filePath = uploadFolderPath + "/" + name;
        File file = new File(filePath);
        file.delete();  //TODO 删除失败的具体处理
        logger.info("deleteFilePath:{}", filePath);
        return "redirect:/onlineoa/zoho/file";
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

    @RequestMapping(value = "/file/{name}", params = "method=edit")
    public void edit(@PathVariable("name") String name, PrintWriter out) throws IOException {
        String subfix = fileSubfix(name);

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(findEditor(subfix));

        StringPart apikey = new StringPart("apikey", "b55679812fe378dc4c288a47dbced340");
        StringPart output = new StringPart("output", "editor");
        StringPart persistence = new StringPart("persistence", "false");
        File file = new File(uploadFolderPath + "/" + name);
        FilePart content = new FilePart("content", name, file);
        StringPart filename = new StringPart("filename", name, "UTF-8");
        StringPart format = new StringPart("format", subfix);
        StringPart id = new StringPart("id", "12345678");
        StringPart saveurl = new StringPart("saveurl", basePath + "/onlineoa/zoho/file?method=save");
        Part[] parts = {apikey, output, persistence, content, filename, format, id, saveurl};
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());

        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        IOUtils.copy(postMethod.getResponseBodyAsStream(), out);
        postMethod.releaseConnection();

        logger.info("editFilePath:{}", file.getPath());
    }

    private final static Map<String, String> EDITORS = new HashMap<String, String>();

    static {
        EDITORS.put("doc,dot,docx,dotx,wps,wpt", "https://xiexie.baihui.com/remotedoc.im");
        EDITORS.put("xls,xlt,xlsx,xltx,et,ett", "https://sheet.baihui.com/remotedoc.im");
        EDITORS.put("ppt,pot,pps,dps,dpt", "https://xiuxiu.baihui.com/remotedoc.im");
    }

    private String fileSubfix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }


    //TODO 文件类型管理功能
    //TODO 文件类型精确判断
    private String findEditor(String type) {
        for (Map.Entry<String, String> entry : EDITORS.entrySet()) {
            if (entry.getKey().contains(type)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 保存文件
     */
    @RequestMapping(value = "/file", params = "method=save")
    public void save(MultipartHttpServletRequest request, PrintWriter out) throws IOException {
        String name = request.getParameter("filename");
        List<MultipartFile> files = request.getFiles("content");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            File uploadFile = new File(uploadFolderPath + "/" + (name != null ? name : clientFile.getOriginalFilename()));
            if (!uploadFile.exists()) {
                uploadFile.createNewFile();
            }
            logger.info("saveFilePath:{}", uploadFile.getPath());
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
        }
    }

}
