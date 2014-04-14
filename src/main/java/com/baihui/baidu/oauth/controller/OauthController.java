package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.oauth.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 百度开放授权接口控制器
 *
 * @author xiayouxue
 * @date 2014/4/2 15:32
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
    public String token(@RequestParam(value = "code") String code,
                        HttpSession session) throws IOException {
        logger.info("获取token");
        logger.info("\t取得授权码，authcode={}", code);
        session.setAttribute(Constant.VS_BAIDU_OAUTH_AUTHCODE, code);

        String accessTokenUrl = oauthService.getAccessTokenUrl(code);
        logger.info("\t获取token，URL={}", accessTokenUrl);
        Map<String, String> accessToken = oauthService.getAccessToken(accessTokenUrl);
        session.setAttribute(Constant.VS_BAIDU_OAUTH_TOKEN, accessToken);
        session.setAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN, accessToken.get("access_token"));

        Object url = session.getAttribute(Constant.VS_BAIDU_OAUTH_URL);
        if (url == null) {
            String defaultUrl = "/baidu/oauth/index";
            logger.info("跳转至默认视图，URL={}", defaultUrl);
            return defaultUrl;
        }
        logger.info("跳转至原业务视图，URL={}", url);

        return "redirect:" + url;
    }


}
