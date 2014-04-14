package com.baihui.docs4baidu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 首页
 *
 * @author xiayouxue
 * @date 2014/4/9 11:22
 */
@Controller
@RequestMapping(value = "")
public class IndexController {
    @RequestMapping(value = "/index")
    public String index(Model model) {
        Map<String, String> fileMap = new LinkedHashMap<String, String>();
        fileMap.put("百度网盘对接", "/editor/baidu");
        fileMap.put("编辑器", "/editor");
        fileMap.put("编辑器->文件管理", "/file");
        fileMap.put("编辑器->文件编辑", "/file4editor");
        fileMap.put("百度权限->授权", "");
        fileMap.put("百度权限->文件", "/baidu/pcs");
        model.addAttribute("files", fileMap);
        return "/index";
    }
}