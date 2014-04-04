package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 百度开放认证
 *
 * @author xiayouxue
 * @date 2014/4/2 15:32
 */
@Controller
@RequestMapping(value = "/baidu/oauth")
public class OauthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OauthService oauthService;

    /**
     * 获取授权码
     */
    @RequestMapping(value = "authcode")
    public String authorizationCode() {
        return "baidu/oauth/authorization_code";
    }

    /**
     * 百度开放认证接口
     */
    @RequestMapping(value = "")
    public String list(Model model) {
        //请求认证代码
        String codeUrl = oauthService.getAuthorizationCodeUrl(oauthService.getCodeRedirectUri());
        model.addAttribute("codeUrl", codeUrl);
        logger.info("AuthorizationCodeUrl:{}", codeUrl);
        return "baidu/oauth/index";
    }

    @RequestMapping(value = "/result")
    public String result(@RequestParam(value = "code") String code, HttpServletRequest request) throws IOException {
        //处理认证代码返回结果
        request.setAttribute("code", code);
        logger.info("authorizationCode:{}", code);

        //请求访问令牌
        Map<String, String> resultMap = oauthService.getAccessToken(oauthService.getAccessTokenUrl(code, oauthService.getTokenRedirectUri()));
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
