/**
 * @Title: ZohoController.java
 * @Package com.baihui.docs4baidu.onlineoa.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 16:07
 * @version V1.0
 */
package com.baihui.docs4baidu.onlineoa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 百会在线办公
 *
 * @author xiayouxue
 * @version version 1.0
 * @ClassName:com.baihui.docs4baidu.onlineoa.controller.ZohoController.java
 * @company 北京百会纵横科技有限公司</p>
 * @copyright 本文件归属 北京百会纵横科技有限公司</p>
 * @since(该版本支持的 JDK 版本) ： 1.6
 * @date 2014-03-25 16:07
 * @modify version 1.0
 */
@Controller
@RequestMapping(value = "/file")
public class BaihuiController {

    /**
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(){
               return null;
    }

}
