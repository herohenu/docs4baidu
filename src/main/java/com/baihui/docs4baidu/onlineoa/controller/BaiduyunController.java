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

import java.io.File;

/**
 * 功能描述：
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
public class BaiduyunController {

    /**
     * 权限认证（百会）
     * 入参：合作单位
     * 出参：是否通过
     * 通过->编辑器，未通过->无权限界面
     */
    public String authorityFilterBaihui() {
        return null;
    }


    /**
     * 身份认证
     * 入参：用户秘钥
     * 出参：是否通过
     * 认证通过->授权界面；认证未通过->无权界面
     */
    public String identityAuthentication() {
        return null;
    }

    /**
     * 权限认证
     * 入参：用户秘钥
     * 出参：是否通过
     */
    public String authorityFilter() {
        return null;
    }

    /**
     * 检查用户授权
     * 入参：用户秘钥
     * 出参：是否授权
     * 是->编辑界面，否->授权界面
     */
    public boolean checkUserAuthorization() {
        return false;
    }


    /**
     * 编辑
     * 入参：文件路径
     * 出参：编辑界面
     */
    public String edit() {
        return null;
    }

    /**
     * 下载文件
     * 入参：文件路径
     * 出参：文件
     */
    public File downloadFile() {
        return null;
    }

    /**
     * 保存文件
     * 入参：文件流
     * 出参：成功|失败
     */
    public boolean saveFile() {
        return false;
    }


}
