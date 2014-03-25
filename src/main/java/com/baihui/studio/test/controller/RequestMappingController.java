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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试请求映射
 * 提供一些连接，点击后跳转到预定的页面
 * 该功能首页为index.jsp，在首页上显示连接列表
 *
 * @author xiayouxue
 * @version version 1.0
 * @ClassName:com.baihui.studio.test.controller.RequestMapping.java
 * @company 北京百会纵横科技有限公司</p>
 * @copyright 本文件归属 北京百会纵横科技有限公司</p>
 * @since(该版本支持的 JDK 版本) ： 1.6
 * @date 2014-03-25 14:13
 * @modify version 1.0
 */
@Controller
@RequestMapping(value = "/test/requestmapping")
public class RequestMappingController {

    @RequestMapping(value = "/index")
    public String indexForm() {
        return "/test/requestmapping/index";
    }

    @RequestMapping(value = "/list")
    public String listForm() {
        return "/test/requestmapping/list";
    }

    @RequestMapping(value = "/create")
    public String createForm() {
        return "/test/requestmapping/create";
    }

    @RequestMapping(value = "/view")
    public String viewForm() {
        return "/test/requestmapping/view";
    }

    @RequestMapping(value = "/update")
    public String updateForm() {
        return "/test/requestmapping/update";
    }

    @RequestMapping(value = "/delete")
    public String deleteForm() {
        return "/test/requestmapping/delete";
    }

}
