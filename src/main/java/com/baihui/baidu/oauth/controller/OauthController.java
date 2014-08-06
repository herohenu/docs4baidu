package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.oauth.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 百度开放授权接口控制器
 *
 * @author xiayouxue
 * @date 2014/4/2
 */
@Controller
@RequestMapping(value = "/baidu/oauth")
@SessionAttributes(value = {Constant.VS_BAIDU_OAUTH_TOKEN, Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN})
public class OauthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OauthService oauthService;

    /**
     * 获取token
     */
    @RequestMapping(value = "/token")
    public String token(String code, @RequestParam(value = "redirect_uri") String redirectUri, ModelMap modelMap) throws IOException {
        logger.info("获取token");
        logger.info("\t授权码={}，业务请求={}", code, redirectUri);

        String tokenRedirectUri = oauthService.getCodeRedirectUri() + "?redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8");
        String accessTokenUrl = oauthService.getAccessTokenUrl(code, URLEncoder.encode(tokenRedirectUri, "utf-8"));
        logger.info("\t获取token，URL={}", accessTokenUrl);
        Map<String, String> accessToken = oauthService.getAccessToken(accessTokenUrl);
        modelMap.addAttribute(Constant.VS_BAIDU_OAUTH_TOKEN, accessToken);
        modelMap.addAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN, accessToken.get("access_token"));
        logger.info("redirectUri={}", redirectUri);
        String forward = "forward:" + "/editor/baidu" + redirectUri.substring(redirectUri.indexOf("?")) + "&method=open";
        logger.info("forward={}", forward);
        return forward;
    }


}
