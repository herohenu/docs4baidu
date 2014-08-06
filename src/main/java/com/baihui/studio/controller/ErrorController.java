package com.baihui.studio.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 错误页面控制器
 *
 * @author xiayouxue
 * @date 2014/4/25
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/adapter")
    public void adapter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("异常适配处理");
        Exception exception = (Exception) request.getAttribute("exception");
        logger.error("捕获到异常", exception);

        logger.info("返回失败结果");
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            String result = "";
            logger.debug("是一个Ajax请求，返回JSON对象“{}”", result);
            response.getWriter().write(result);
            return;
        }

        String errorForwardUri = request.getParameter("errorForwardUri");
        if (StringUtils.isBlank(errorForwardUri)) {
            errorForwardUri = "/error/500";
        }
        request.setAttribute("VM_ALL", exception.getMessage());
        logger.debug("是一个常规请求，转至“{}”页面", errorForwardUri);
        request.getRequestDispatcher(errorForwardUri).forward(request, response);
    }

    @RequestMapping(value = "/{code}")
    public String index(@PathVariable(value = "code") String code) {
        logger.info("转发至错误页面“{}”", code);
        return "/error/" + code;
    }


}
