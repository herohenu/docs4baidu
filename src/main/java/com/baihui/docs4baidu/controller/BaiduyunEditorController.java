package com.baihui.docs4baidu.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.pcs.service.PcsService;
import com.baihui.editor.entity.Editor;
import com.baihui.editor.service.EditorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.io.FilenameUtils;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 百度网盘编辑器控制类
 * //TODO doc保存时，弹出返回结果
 * //TODO doc打开时，报无法导入文件，有时
 * //TODO 关于异常的情况
 *
 * @author xiayouxue
 * @date 2014/4/14 10:10
 */
@Controller
@RequestMapping(value = "")
public class BaiduyunEditorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EditorService editorService;
    @Resource
    private PcsService pcsService;

    @ModelAttribute("token")
    public String token(HttpSession session) {
        return (String) session.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
    }

    /**
     * 用编辑器打开文件
     * 1.写格秀三种类型
     * 2.文件名中文乱码
     */
    @RequestMapping(value = "/editor/baidu", method = {RequestMethod.GET}, params = {"method=open"})
    public String open(@RequestParam(value = "path") String path,
                       @ModelAttribute(value = "token") String token,
                       Model model) throws IOException, ParserException {
        String com = "zoho";
        logger.info("使用{}编辑器打开文件，path={}", com, path);
        String extension = FilenameUtils.getExtension(path);
        Editor editor = editorService.findEditorByComExtension(com, extension);
        String encodePath = URLEncoder.encode(path, "utf-8");
        editor.setFilePath(encodePath);
        editor.setFileName(FilenameUtils.getName(path));
        editor.setFormat(extension);
        editor.setDownloadUrl(pcsService.getDownloadUrl(token, encodePath));
        editor.setSaveUrl(pcsService.getUploadUrl(token, encodePath));
        logger.info("\t编辑器详情={}", new ObjectMapper().writeValueAsString(editor));
        model.addAttribute("editor", editor);
        return "/editor/form";
    }


}
