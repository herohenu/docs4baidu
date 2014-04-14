package com.baihui.baidu.pcs.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.oauth.service.OauthService;
import com.baihui.baidu.pcs.service.PcsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 百度个人云存储控制类
 *
 * @author xiayouxue
 * @date 2014/4/2 15:32
 */
@Controller
@RequestMapping(value = "/baidu/pcs")
public class PcsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PcsService pcsService;

    @ModelAttribute("token")
    public String token(HttpSession session) {
        return (String) session.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
    }

    /**
     * 显示文件列表
     */
    @RequestMapping(value = "")
    public String list(Model model, @ModelAttribute(value = "token") String token) {
        Map<String, String> fileMap = new LinkedHashMap<String, String>();
        logger.info("文件列表，files={}", pcsService.getFiles());
        String[] files = pcsService.getFiles().split(",");
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            fileMap.put(file, pcsService.getDownloadUrl(token, file));
        }
        model.addAttribute("files", fileMap);
        return "/baidu/pcs/index";
    }


}
