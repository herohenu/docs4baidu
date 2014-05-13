package com.baihui.docs4baidu.fileview.controller;

import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.docs4baidu.fileview.service.FileviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 文件访问信息控制器
 *
 * @author xiayouxue
 * @date 2014/5/12
 */
@Controller
@RequestMapping("/fileview")
public class FileviewController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FileviewService fileviewService;

    @RequestMapping("/list")
    public String list(Model model) throws JsonProcessingException {
        String topage = "/docs4baidu/fileview/list";
        logger.info("跳转文件访问信息列表页“{}”", topage);
        Iterable<Fileview> fileviews = fileviewService.findAll();
        logger.debug("文件访问信息列表“{}”", new ObjectMapper().writeValueAsString(fileviews));
        model.addAttribute("fileviews", fileviews);
        return "/docs4baidu/fileview/list";
    }
}
