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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    public final static String XIEXIE_URL = "https://exportwriter.zoho.com/remotedoc.im";
    public final static String SHEET_URL = "https://sheet.zoho.com/remotedoc.im";
    public final static String XIUXIU_URL = "https://show.zoho.com/remotedoc.im";
    public final static String APIKEY = "b55679812fe378dc4c288a47dbced340";

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/file/list")
    public String fileList(HttpServletRequest request) {
        request.setAttribute("files", getFiles(request));
        return "/onlineoa/zoho/fileList";
    }

    private File[] getFiles(HttpServletRequest request) {
        String uploadFolderPath = request.getSession().getServletContext().getRealPath("/upload");//TODO 固定的上传目录，一次搞定的
        File uploadFolder = new File(uploadFolderPath);
        if (!uploadFolder.exists()) uploadFolder.mkdir(); //TODO 创建目录失败的处理
        logger.debug("uploadFolderPath:{}", uploadFolderPath);
        return uploadFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    @RequestMapping(value = "/file/upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        List<MultipartFile> files = request.getFiles("filePath");
        String uploadFolderPath = request.getSession().getServletContext().getRealPath("/upload");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            File uploadFile = new File(uploadFolderPath + "/" + clientFile.getOriginalFilename());
            if (!uploadFile.exists()) uploadFile.createNewFile();
            logger.debug("uploadFilePath:{}", uploadFile.getPath());
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
        }
        return "redirect:/onlineoa/zoho/file/list";
    }

    @RequestMapping(value = "/file/delete")
    public String delete(HttpServletRequest request, @RequestParam("name") String name) {
        String uploadFolderPath = request.getSession().getServletContext().getRealPath("/upload");
        String filePath = uploadFolderPath + "/" + name;
        File file = new File(filePath);
        file.delete();
        return "redirect:/onlineoa/zoho/file/list";
    }

    @RequestMapping(value = "/file/download")
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name) throws IOException {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
        String uploadFolderPath = request.getSession().getServletContext().getRealPath("/upload");
        String filePath = uploadFolderPath + "/" + name;
        logger.debug("downloadFilePath:{}", filePath);
        File file = new File(filePath);
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }


    @RequestMapping(value = "/file/edit")
    public void edit(HttpServletRequest request, @RequestParam("name") String name) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        //把一个普通参数和文件上传给下面这个地址    是一个servlet
        HttpPost httpPost = new HttpPost(findEditor(fileSubfix(name)));
        //把文件转换成流对象FileBody
        FileBody bin = new FileBody(new File(filePath));
        //普通字段  重新设置了编码方式
        StringBody comment = new StringBody("这里是一个评论", ContentType.create("text/plain", Consts.UTF_8));
        //StringBody comment = new StringBody("这里是一个评论", ContentType.TEXT_PLAIN);

        StringBody name = new StringBody("王五", ContentType.create("text/plain", Consts.UTF_8));
        StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("media", bin)//相当于<input type="file" name="media"/>
                .addPart("comment", comment)
                .addPart("name", name)//相当于<input type="text" name="name" value=name>
                .addPart("password", password)
                .build();

        httpPost.setEntity(reqEntity);
    }

    private final static Map<String, String> EDITORS = new HashMap<String, String>();

    static {
        EDITORS.put("doc,dot,docx,dotx,wps,wpt", XIEXIE_URL);
        EDITORS.put("xls,xlt,xlsx,xltx,et,ett", SHEET_URL);
        EDITORS.put("ppt,pot,pps,dps,dpt", XIUXIU_URL);
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

    @RequestMapping(value = "/file/save")
    public void save(MultipartHttpServletRequest request) throws IOException {
        List<MultipartFile> files = request.getFiles("filePath");
        String uploadFolderPath = request.getSession().getServletContext().getRealPath("/upload");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            File uploadFile = new File(uploadFolderPath + "/" + clientFile.getOriginalFilename());
            if (!uploadFile.exists()) uploadFile.createNewFile();
            logger.debug("uploadFilePath:{}", uploadFile.getPath());
            FileUtils.copyInputStreamToFile(clientFile.getInputStream(), uploadFile);
        }
    }


}
