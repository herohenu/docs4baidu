package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.oauth.service.OauthService;
import com.baihui.studio.util.UrlPathUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权拦截器
 * 1.在每一次对百度访问时，检查是否有访问令牌
 *
 * @author xiayouxue
 * @date 2014/4/4 10:07
 */

public class BaiduOauthIntercetor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OauthService oauthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        logger.info("检查百度授权，URL={}", url);
        //授权码响应请求不检查token
        if (url.equals(oauthService.getCodeRedirectUri())) {
            logger.info("\t是一个授权码响应请求->不检查session中token\n");
            return true;
        }

        String[] ignoreUris = oauthService.getAccessTokenIgnoreUris().split(",");
        if (ArrayUtils.contains(ignoreUris, url)) {
            logger.info("\t是一个忽略的请求->不检查session中token\n");
            return true;
        }

        logger.info("\t是一个业务请求->检查session中token");
        //检查token->未存储token->获取token
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN) == null) {
            logger.info("\t\t未存储token");
            //缓存业务请求URL->在取得token后可继续执行
            String serviceUrl = UrlPathUtil.appendParam(url, UrlPathUtil.toStringParams(request.getParameterMap()));
            session.setAttribute(Constant.VS_BAIDU_OAUTH_URL, serviceUrl);
            logger.info("\t\t缓存业务请求，URL={}", serviceUrl);

            //重定向到授权码请求
            String authorizationCodeUrl = oauthService.getAuthorizationCodeUrl();
            response.sendRedirect(authorizationCodeUrl);
            logger.info("\t\t重定向到授权码请求，URL={}\n", authorizationCodeUrl);
            return false;
        }
        logger.info("\t\t已存储token");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
