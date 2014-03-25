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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
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

    public final static String XIEXIE_URL = "https://xiexie.baihui.com/remotedoc.im";
    public final static String SHEET_URL = "https://xiexie.baihui.com/remotedoc.im";
    public final static String XIUXIU_URL = "https://xiexie.baihui.com/remotedoc.im";
    public final static String APIKEY = "b55679812fe378dc4c288a47dbced340";

    @RequestMapping(value = "/filelist")
    public String fileList(HttpServletRequest request) {
        File file = new File(request.getSession().getServletContext().getRealPath(request.getContextPath()) + "/upload");
        if (!file.exists()) file.mkdirs();

        return "/onlineoa/zoho/fileList";
    }

    @RequestMapping(value = "/upload")
    public String upload(MultipartHttpServletRequest request, @RequestParam("name") String name) {
        List<MultipartFile> files = request.getFiles("file");
        String serverPath = request.getSession().getServletContext().getRealPath("/") + "/upload";
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

        }
//        request.setAttribute("uploadFiles", uploadFiles);
        return "/onlineoa/zoho/fileList";
    }

    @RequestMapping(value = "/download")
    public String download(MultipartHttpServletRequest request, @RequestParam("name") String name) {
        List<MultipartFile> files = request.getFiles("file");
        String serverPath = request.getSession().getServletContext().getRealPath("/") + "/upload";
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

        }
//        request.setAttribute("uploadFiles", uploadFiles);
        return "/onlineoa/zoho/fileList";
    }

    @RequestMapping(value = "/edit")
    public String edit(){
        return "/onlineoa/zoho/fileList";
    }


}
