package com.baihui.docs4baidu.editor.controller;

import com.baihui.baidu.oauth.service.OauthService;
import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.service.EditorServiceImpl;
import com.baihui.studio.controller.ControllerException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 百度网盘编辑器控制类
 *
 * @author xiayouxue
 * @version version 1.0
 */
@Controller
@RequestMapping(value = "")
public class BaiduyunEditorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadFolderPath;

    @Value(value = "${editor.titlebar.id}")
    private String titlebarId;

    @Resource
    private EditorServiceImpl editorService;

    @Resource
    private OauthService oauthService;


    /**
     * 编辑器打开文件
     * 1.获取授权码
     * 2.获取访问令牌
     * 3.打开文件
     * <p/>
     * //TODO 将授权统一使用filter处理应该是比较好的方式
     */
    @RequestMapping(value = "/editor/baidu", params = {"method=open"})
    public String open(@RequestParam(value = "path", defaultValue = "") String path,
                       @RequestParam(value = "code", defaultValue = "") String code,
                       PrintWriter out) throws IOException, ParserException {

        //请求授权码<-未获取授权码
        if (StringUtils.isEmpty(code) == true) {
            String codeRedirectUri = String.format(editorService.getCodeRedirectUri(), path);
            String authorizationCodeUrl = oauthService.getAuthorizationCodeUrl(URLEncoder.encode(codeRedirectUri, "utf-8"));

            logger.info("authorizationCodeUrl:{}", authorizationCodeUrl);
            return "redirect:" + authorizationCodeUrl;//TODO 关于URL?后?，?转义
        }

        //请求访问令牌<-取得授权码
        String tokenRedirectUri = String.format(editorService.getTokenRedirectUri(), path);
        String accessTokenUrl = oauthService.getAccessTokenUrl(code, URLEncoder.encode(tokenRedirectUri, "utf-8"));
        logger.info("accessTokenUrl:{}", accessTokenUrl);
        Map<String, String> tokenMap = oauthService.getAccessToken(accessTokenUrl);
        if (tokenMap.containsKey("error") == true) {
            logger.error("error:{}", tokenMap.get("error"));
            throw new ControllerException("获取访问令牌异常！");
        }

        if (1 == 2) {
            //使用编辑器打开文件->需要文件路径path->在redirecUri后附加path
            String extension = FilenameUtils.getExtension(path);
            Editor editor = editorService.findEditorByComExtension("zoho", extension).clone();
            editor.setWay("remote");
            editor.setFilename(FilenameUtils.getName(path));
            logger.info("根据文件扩展名查找对应编辑器，editorName:{}", editor.getName());

            String fileUrl = String.format(editorService.getDownloadUrl(), tokenMap.get("access_token"), path);//TODO URL使用自身或者百度
            editor.setFileUrl(fileUrl);
            logger.info("fileUrl:{}", fileUrl);

            String content = editorService.open(editor);
            IOUtils.write(content, out);
        }


        String fileUrl = String.format(editorService.getDownloadUrl(), tokenMap.get("access_token"), URLEncoder.encode(path,"utf-8"));
        logger.info("fileUrl:{}", fileUrl);
        return "redirect:" + fileUrl;
    }


    /**
     * 编辑器框架布局
     */
    @RequestMapping(value = "/editor/baidu", params = {"method=layout"})
    public String layout(@RequestParam(value = "way") String way, Model model) {
        model.addAttribute("titlebarId", titlebarId);
        return "/baidu/editor" + (StringUtils.isEmpty(way) == true ? "" : ("-" + way));
    }

    @RequestMapping(value = "/editor/baidu/{type}")
    public String header(@PathVariable(value = "type") String editorType) {
        return "/baidu/header";
    }

    /**
     * 编辑器打开文件，本地测试使用
     * //TODO 不能连接到服务器
     */
    @RequestMapping(value = "/editor", params = {"method=open", "env=local"})
    public void openLocal(@RequestParam(value = "com") String com,
                          @RequestParam(value = "way") String way,
                          @RequestParam(value = "name") String name,
                          PrintWriter out) throws IOException, ParserException {
        File file = new File(uploadFolderPath + "/" + name);
        logger.info("读取编辑器编辑文件，filePath:{}", file.getPath());

        String extension = FilenameUtils.getExtension(file.getName());
        Editor editor = editorService.findEditorByComExtension(com, extension);
        logger.info("根据文件扩展名查找对应编辑器，editorName:{}", editor.getName());

        editor.setContent(file);
        String content = editorService.open(editor);
        if ("modify-java".equals(way) == false) {
            IOUtils.write(content, out);
            return;
        }

        String url = content.substring(content.indexOf("https"), content.lastIndexOf("'"));
        logger.info("内容非编辑器HTML代码，而且一段脚本链接请求，通过链接再次获取HTML代码，url:{}", url);

        logger.info("屏蔽标题栏->删除标题栏->标题栏id:{}", titlebarId);
        Parser parser = new Parser(new URL(url).openConnection());
        NodeList titleBarNodeList = parser.extractAllNodesThatMatch(new HasAttributeFilter("id", titlebarId));
        if (titleBarNodeList.size() == 0) {
            logger.warn("无法获取到标题栏id:{}，可能zoho编辑器代码已调整，请进行更新", titlebarId);
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
    }


}
