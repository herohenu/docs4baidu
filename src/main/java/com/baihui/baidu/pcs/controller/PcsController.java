package com.baihui.baidu.pcs.controller;

import com.baihui.baidu.oauth.service.OauthService;
import com.baihui.baidu.pcs.service.PcsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 百度云
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

    @Resource
    private OauthService oauthService;


    /**
     * 百度开放认证接口
     */
    @RequestMapping(value = "")
    public String list(@RequestParam(value = "code") String code,
                       Model model) {
        //请求授权码<-未获取授权码
        if (StringUtils.isEmpty(code) == true) {
            String codeRedirectUri = String.format(editorService.getCodeRedirectUri(), path);
            String authorizationCodeUrl = oauthService.getAuthorizationCodeUrl(URLEncoder.encode(codeRedirectUri, "utf-8"));

            logger.info("authorizationCodeUrl:{}", authorizationCodeUrl);
            return "redirect:" + authorizationCodeUrl;//TODO 关于URL?后?，?转义
        }
        List<String> urls = new ArrayList<String>();
        urls.add("/apps");
        return "baidu/pcs/index";
    }

    @RequestMapping(value = "/result")
    public String result(@RequestParam(value = "code") String code, HttpServletRequest request) throws IOException {
        //处理认证代码返回结果
        request.setAttribute("code", code);
        logger.info("authorizationCode:{}", code);

        //请求访问令牌
        Map<String, String> resultMap = pcsService.getAccessToken(pcsService.getAccessTokenUrl(code, pcsService.getTokenRedirectUri()));
        if (resultMap.containsKey("error") == true) {
            String error = resultMap.get("error");
            logger.info("error:{}", error);
            request.setAttribute("error", error);
            return "baidu/oauth/index";
        }
        String token = resultMap.get("access_token");
        request.setAttribute("token", token);
        logger.info("accessToken:{}", token);
        return "baidu/oauth/index";
    }

}
