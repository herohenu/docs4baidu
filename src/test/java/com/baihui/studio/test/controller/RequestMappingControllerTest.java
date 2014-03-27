/**
 * @Title: RequestMappingControllerTest.java
 * @Package com.baihui.studio.test.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:北京百会纵横科技有限公司
 *
 * @author xiayouxue
 * @date 2014-03-25 15:21
 * @version V1.0
 */
package com.baihui.studio.test.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 功能描述：
 *
 * @author xiayouxue
 * @version version 1.0
 * @ClassName:com.baihui.studio.test.controller.RequestMappingControllerTest.java
 * @company 北京百会纵横科技有限公司</p>
 * @copyright 本文件归属 北京百会纵横科技有限公司</p>
 * @since(该版本支持的 JDK 版本) ： 1.6
 * @date 2014-03-25 15:21
 * @modify version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class RequestMappingControllerTest extends AbstractJUnit4SpringContextTests{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${application.basePath}")
    private String basePath;

    @Test
    public void testSlf4j() throws Exception {
        logger.info("basePath:{}", basePath);

    }
}
