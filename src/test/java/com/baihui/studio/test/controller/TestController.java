/**
 * @Title: RequestMappingController.java
 * @Package com.baihui.studio.test.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 14:13
 * @version V1.0
 */
package com.baihui.studio.test.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试
 *
 * @author xiayouxue
 * @date 2014-03-25 14:13
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/index")
    public String index(Model model) {
        Map<String, String> urls = new LinkedHashMap<String, String>();
        urls.put("inputstream", "/inputstream?key=name&value=name");
        urls.put("requestmapping", "/requestmapping/index");
        model.addAttribute("urls", urls);
        return "/test/index";
    }

    @RequestMapping(value = "/inputstream")
    public String inputStream(HttpServletRequest request) throws IOException {
        logger.info("content:{}", IOUtils.toString(request.getInputStream()));
        logger.info("key:{},value:{}", "name", request.getParameter("key"));
        return "/test/index";
    }

}
