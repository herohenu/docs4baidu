package com.baihui.docs4baidu.controller;

import com.baihui.baidu.oauth.Constant;
import com.baihui.baidu.pcs.service.PcsService;
import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.docs4baidu.fileview.service.FileviewService;
import com.baihui.editor.entity.Editor;
import com.baihui.editor.service.EditorService;
import com.baihui.studio.utils.mapper.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FilenameUtils;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
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
    @Resource
    private FileviewService fileviewService;

    @ModelAttribute("token")
    public String token(HttpSession session) {
        return (String) session.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
    }

    /**
     * 用编辑器打开文件
     */
    @RequestMapping(value = "/editor/baidu", method = {RequestMethod.GET}, params = {"method=open"})
    public String open(@RequestParam(value = "path") String path,
//                       @ModelAttribute(value = Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN) String token,
                       HttpServletRequest request,
                       Model model) throws IOException {

        String token = (String) request.getAttribute(Constant.VS_BAIDU_OAUTH_TOKEN_TOKEN);
//        path = "/docs4baidu/test1.doc";
        String com = editorService.getCom();
        logger.info("使用{}编辑器打开文件，path={}", com, path);
        String extension = FilenameUtils.getExtension(path);
        Editor editor = editorService.findEditorByComExtension(com, extension);
        String encodePath = URLEncoder.encode(path, "utf-8");

        editor.setFileName(FilenameUtils.getName(path));
        editor.setFormat(extension);
        editor.setDownloadUrl(pcsService.getDownloadUrl(token, encodePath));
        editor.setSaveUrl(editorService.getSaveUrl(token, encodePath));
        logger.info("\t编辑器详情={}", JsonMapper.nonEmptyMapper().toJson(editor));
        model.addAttribute("editor", editor);

        try {
            logger.info("保存文件访问信息");
            Fileview fileview = new Fileview();
            fileview.setPath(path);
            fileview.setSize(0l);
            fileview.setType(editor.getType());
            fileview.setAccesstoken(token);
            fileview.setCreateTime(new Date());
            fileviewService.save(fileview);
            logger.info("文件访问信息“{}”", fileview);
            editor.setId(String.valueOf(fileview.getId()));
        } catch (Exception e) {
            logger.error("保存失败", e);
        }

        return "/docs4baidu/form";
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 编辑后保存文件
     * -失败后重新尝试3次
     * -给出失败提示
     * -文件未编辑便保存，如果传回了空文件，文件大小为0，不调用上传接口，直接提示保存成功
     */
    @RequestMapping(value = "/editor/save", method = {RequestMethod.POST})
    public void save(MultipartHttpServletRequest request, PrintWriter out) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        String result = "{\"error_code\":31066,\"error_msg\":\"file does not exist\"}";
        try {
            String path = request.getParameter("path"); //TODO 需要再编码不
            path = path.trim();
            logger.info("编辑后保存文件“{}”", path);
            Map<String, MultipartFile> fileMap = request.getFileMap();
            Part[] parts = new Part[fileMap.size()];
            int i = 0;
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile clientFile = entry.getValue();
                logger.info("\t文件大小={}", clientFile.getSize());
                if (clientFile.getSize() == 0L) {
                    return;
                }
                logger.info("\t表单名称={}，文件名称={}", entry.getKey(), clientFile.getOriginalFilename());
                parts[i++] = new FilePart("file", new ByteArrayPartSource(clientFile.getOriginalFilename(), clientFile.getBytes()));
            }

            String token = request.getParameter("access_token");
            System.setProperty("javax.net.ssl.trustStore", pcsService.getTrustpath());
            logger.info("\tjavax.net.ssl.trustStore:{}", pcsService.getTrustpath());
            /*
            错误的： {"error_code":31066,"error_msg":"file does not exist","extra":{"list":[]},"request_id":2298483506}
            正确的： {"path":"\/a.txt","size":5370,"ctime":1409813795,"mtime":1409813795,"md5":"3c862d46d0b9b8ad280bde9f28aa5990","fs_id":60043735986081,"isdir":0,"request_id":3929050687}
            */
            Map resultMap = new HashMap();
            for (int j = 0; j < 3; j++) {
                logger.info("尝试第{}次保存", j + 1);
                result = pcsService.upload(token, path, parts);
                logger.info("\tresult:{}", result);
                //如果失败，重新尝试提交
                resultMap = objectMapper.readValue(result, Map.class);
                if (!resultMap.containsKey("error_code")) {
                    logger.info("保存成功");
                    break;
                } else {
                    logger.warn("上传到百度服务器失败{}", result);
                    try {
                        int time = 1;
                        Thread.sleep(1000L * time);
                        logger.info("休眠{}后继续尝试", time);
                    } catch (InterruptedException e) {
                        logger.warn("休眠失败");
                    }
                }
            }

            if (resultMap.containsKey("error_code")) {
                out.write("保存失败，请稍后尝试！");
            }


            String id = request.getParameter("id");
            logger.info("id={}", id);
//            if (StringUtils.isNoneBlank(id)) {
//                Fileview fileview = fileviewService.get(Long.parseLong(id));
//                if (fileview != null) {
//                    fileview.setSize(fileMap.entrySet().iterator().next().getValue().getSize());
//                    fileviewService.save(fileview);
//                }
//            }

        } catch (IOException e) {
            logger.error("", e);
            out.write(result);
        }
    }


}
