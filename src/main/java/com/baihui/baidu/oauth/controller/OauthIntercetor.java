package com.baihui.baidu.oauth.controller;

import com.baihui.baidu.oauth.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 授权拦截器
 * 1.在每一次对百度的访问时，检查是否有访问令牌
 *
 * @author xiayouxue
 * @date 2014/4/4 10:07
 */

public class OauthIntercetor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OauthService oauthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("TOKEN");
        //获取token->未获取token
        if (token == null) {
            //业务请求->不是一个授权码响应请求->为授权码响应请求附加特定参数进行标识，并且授权码响应请求为当前业务请求，在授权后才能跳转回当前业务请求
            String authcodeRequestMark = request.getParameter("AUTHCODEREQUEST_MARK");
            String markUrl = markUrl(request);
            if ("true".equals(authcodeRequestMark) == false) {
                logger.info("业务请求URL:{}");
                String authorizationCodeUrl = oauthService.getAuthorizationCodeUrl(markUrl);
                logger.info("authorizationCodeUrl:{}", authorizationCodeUrl);
                //请求授权码
                response.sendRedirect(authorizationCodeUrl);
                return false;
            }

            //授权码响应请求
            String code = request.getParameter("code");
            String accessTokenUrl = oauthService.getAccessTokenUrl(code, markUrl);
            logger.info("accessTokenUrl:{}", accessTokenUrl);
            Map<String, String> accessToken = oauthService.getAccessToken(accessTokenUrl);
            session.setAttribute("TOKEN_MAP", accessToken);
            session.setAttribute("TOKEN", accessToken.get("access_token"));
        }
        return true;
    }

    public String markUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String mark = fullUrl(request);
        mark = appendParam(mark, "AUTHCODEREQUEST_MARK=true");
        mark = URLEncoder.encode(mark, "utf-8");
        return mark;
    }

    public String fullUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String params = params(request.getParameterMap());
        if (params.length() > 0) {
            url.append("?" + params);
        }
        return url.toString();
    }

    public String params(Map<String, String[]> parameterMap) {
        StringBuffer params = new StringBuffer();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            for (String value : entry.getValue()) {
                params.append(String.format("&%s=%s", entry.getKey(), value));
            }
        }
        return params.length() > 0 ? params.substring(1) : params.toString();
    }

    public String appendParam(String url, String item) {
        if (url.indexOf("?") == -1) {
            return url + "?" + item;
        }
        return url + "&" + item;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
