package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.oauth.service.OauthService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 授权拦截器
 * 1.在每一次对百度访问时，检查是否有访问令牌
 *
 * @author xiayouxue
 * @date 2014/4/4
 */

public class BaiduOauthIntercetor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OauthService oauthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        logger.info("检查百度授权，URL={}", url);

        logger.info("检查token");
        Object token = request.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
        if (token != null) {
            logger.info("已取得token，执行业务请求");
            return true;
        }
        logger.info("未取得token，获取token");


        //授权码响应请求不检查token
        if (url.equals(oauthService.getCodeRedirectUri())) {
            logger.info("\t是一个授权码响应请求->不检查token\n");
            return true;
        }

        String[] ignoreUris = oauthService.getAccessTokenIgnoreUris().split(",");
        if (ArrayUtils.contains(ignoreUris, url)) {
            logger.info("\t是一个忽略的请求->不检查token\n");
            return true;
        }

        String path = request.getParameter("path");
//        path = URLEncoder.encode(path, "utf-8");
        String method = request.getParameter("method");
//        String serviceUrl = UrlPathUtil.appendParam(url, UrlPathUtil.toStringParams(request.getParameterMap()));
        String serviceUrl = String.format(url + "?path=%s&method=%s", path, method);
        logger.info("业务请求，URL={}", serviceUrl);

        //重定向到授权码请求
        String redirectUri = oauthService.getCodeRedirectUri() + "?redirect_uri=" + URLEncoder.encode(serviceUrl, "utf-8");
        String authorizationCodeUrl = oauthService.getAuthorizationCodeUrl(URLEncoder.encode(redirectUri, "utf-8"));
        logger.info("\t\t重定向到授权码请求，URL={}\n", authorizationCodeUrl);
        response.sendRedirect(authorizationCodeUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
