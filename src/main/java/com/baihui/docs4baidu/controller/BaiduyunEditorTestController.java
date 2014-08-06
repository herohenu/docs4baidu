package com.baihui.docs4baidu.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.pcs.service.PcsService;
import com.baihui.editor.service.EditorService;
import com.baihui.file.controller.FileController;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度网盘编辑器控制类
 *
 * @author xiayouxue
 * @version version 1.0
 */
@Controller
@RequestMapping(value = "")
public class BaiduyunEditorTestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadFolderPath;

    @Value(value = "${editor.baidu.files}")
    private String files;

    @Resource
    private EditorService editorService;

    @Resource
    private FileController fileController;


    @Resource
    private PcsService pcsService;

    @ModelAttribute("token")
    public String token(HttpSession session) {
        return (String) session.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
    }

    @RequestMapping(value = "/editor/baidu")
    public String list(Model model) throws UnsupportedEncodingException {
        Map<String, String> links = new LinkedHashMap<String, String>();
        String[] files = pcsService.getFiles().split(",");
        logger.info("文件列表，files={}", pcsService.getFiles());
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            links.put(file, editorService.getOpenUrl(file));
        }
        model.addAttribute("links", links);
        return "/editor/index";
    }

    @RequestMapping(value = "/file4editor")
    public String list4editor(Model model) {
        fileController.list(model);
        return "/editor/list4editor";
    }

    /**
     * 编辑器框架布局
     */
    @RequestMapping(value = "/editor/baidu", params = {"method=layout"})
    public String layout(@RequestParam(value = "way") String way, Model model) {
        model.addAttribute("titlebarId", editorService.getTitlebarId());
        return "/baidu/editor" + (StringUtils.isEmpty(way) == true ? "" : ("-" + way));
    }

    @RequestMapping(value = "/editor/baidu/{type}")
    public String header(@PathVariable(value = "type") String editorType) {
        return "/baidu/header";
    }

/*    *//**
     * 编辑器打开文件，本地测试使用
     * //TODO 不能连接到服务器
     *//*
    @RequestMapping(value = "/editor", params = {"method=open", "env=local"})
    public void openLocal(@RequestParam(value = "com") String com,
                          @RequestParam(value = "way") String way,
                          @RequestParam(value = "name") String name,
                          PrintWriter out) throws IOException, ParserException {
        File file = new File(uploadFolderPath + "/" + name);
        logger.info("读取编辑器编辑文件，filePath:{}", file.getPath());

        String extension = FilenameUtils.getExtension(file.getName());
        Editor editor = editorService.findEditorByComExtension(com, extension);

        String content = editorService.open(editor);
        if ("modify-java".equals(way) == false) {
            IOUtils.write(content, out);
            return;
        }

        String url = content.substring(content.indexOf("https"), content.lastIndexOf("'"));
        logger.info("内容非编辑器HTML代码，而且一段脚本链接请求，通过链接再次获取HTML代码，url:{}", url);

        logger.info("屏蔽标题栏->删除标题栏->标题栏id:{}", editorService.getTitlebarId());
        Parser parser = new Parser(new URL(url).openConnection());
        NodeList titleBarNodeList = parser.extractAllNodesThatMatch(new HasAttributeFilter("id", editorService.getTitlebarId()));
        if (titleBarNodeList.size() == 0) {
            logger.warn("无法获取到标题栏id:{}，可能zoho编辑器代码已调整，请进行更新", editorService.getTitlebarId());
            Page page = parser.parse(null).elements().nextNode().getPage();   //TODO 通过Parse.elements获取节点报错
            IOUtils.write(page.getText(), out);
        }

        Node titleBarNode = titleBarNodeList.remove(0);//TODO remove并不能使Page中源码有所改变，只对当前NodeList有影响
        logger.info("取得标题栏HTML代码:{}", titleBarNode.toHtml());

        Page page = titleBarNode.getPage();
        StringBuffer html = new StringBuffer();
        page.getText(html, 0, titleBarNode.getStartPosition() - 1);
        page.getText(html, titleBarNode.getEndPosition() + 1, page.getSource().offset());
        logger.debug("HTML代码:{}", html);
        IOUtils.write(html.toString(), out);
    }*/

    /**
     * 编辑后保存文件
     */
    @RequestMapping(value = "/editor/baidu/save", method = {RequestMethod.POST})
    public void save(MultipartHttpServletRequest request, @ModelAttribute(value = "token") String token) throws IOException {
        logger.info("编辑后保存文件");
        String name = request.getParameter("filename");
        logger.debug("\t原始文件名={}", name);
        name = URLDecoder.decode(name, "utf-8");
        logger.debug("\t转义文件名:{}", name);
        List<MultipartFile> files = request.getFiles("content");
        logger.debug("\t文件数目:{}", files.size());
        Part[] parts = new Part[files.size()];
        for (int i = 0; i < files.size(); i++) {
            MultipartFile clientFile = files.get(i);
            logger.debug("\tclientFile.name:{},client.originalFilename:{}", clientFile.getName(), clientFile.getOriginalFilename());
            parts[i] = new FilePart("file", new ByteArrayPartSource(clientFile.getOriginalFilename(), clientFile.getBytes()));
        }

        String filePath = request.getParameter("filepath");
        filePath = URLDecoder.decode(filePath, "utf-8");
        logger.debug("\t上传文件至百度服务器，URL={}", pcsService.getUploadUrl(token, filePath));
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(pcsService.getUploadUrl(token, name));
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        postMethod.releaseConnection();
    }


}
