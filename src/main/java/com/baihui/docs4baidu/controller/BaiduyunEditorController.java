package com.baihui.docs4baidu.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.pcs.service.PcsService;
import com.baihui.editor.entity.Editor;
import com.baihui.editor.service.EditorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * 百度网盘编辑器控制类
 *
 * @author xiayouxue
 * @date 2014/4/14
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
     */
    @RequestMapping(value = "/editor/baidu", method = {RequestMethod.GET}, params = {"method=open"})
    public String open(@RequestParam(value = "path") String path,
                       @ModelAttribute(value = "token") String token,
                       Model model) throws IOException, ParserException {
        String com = editorService.getCom();
        logger.info("使用{}编辑器打开文件，path={}", com, path);
        String extension = FilenameUtils.getExtension(path);
        Editor editor = editorService.findEditorByComExtension(com, extension);
        String encodePath = URLEncoder.encode(path, "utf-8");
        editor.setFileName(FilenameUtils.getName(path));
        editor.setFormat(extension);
        editor.setDownloadUrl(pcsService.getDownloadUrl(token, path)); //无需编码
        editor.setSaveUrl(pcsService.getUploadUrl(token, encodePath)); //需要编码
        editor.setSaveUrl(editorService.getSaveUrl(token, encodePath));
        logger.info("\t编辑器详情={}", new ObjectMapper().writeValueAsString(editor));
        model.addAttribute("editor", editor);
        return "docs4baidu/form";
    }

    /**
     * 编辑后保存文件
     */
    @RequestMapping(value = "/editor/save", method = {RequestMethod.POST})
    public void save(MultipartHttpServletRequest request, PrintWriter out) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        try {
            String path = request.getParameter("path"); //TODO 需要再编码不
            path = path.trim();
            logger.info("编辑后保存文件“{}”", path);
            Map<String, MultipartFile> fileMap = request.getFileMap();
            Part[] parts = new Part[fileMap.size()];
            int i = 0;
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile clientFile = entry.getValue();
                logger.info("\t表单名称={}，文件名称={}", entry.getKey(), clientFile.getOriginalFilename());
                parts[i++] = new FilePart("file", new ByteArrayPartSource(clientFile.getOriginalFilename(), clientFile.getBytes()));
            }

            String token = request.getParameter("access_token");
            System.setProperty("javax.net.ssl.trustStore", pcsService.getTrustpath());
            logger.info("\tjavax.net.ssl.trustStore:{}", pcsService.getTrustpath());
            String result = pcsService.upload(token, path, parts);
            logger.info("\tresult:{}", result);
        } catch (IOException e) {
            logger.error("", e);
        }
    }


}
