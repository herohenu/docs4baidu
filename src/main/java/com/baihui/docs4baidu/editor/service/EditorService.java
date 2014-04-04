package com.baihui.docs4baidu.editor.service;

import com.baihui.docs4baidu.editor.entity.Editor;

import java.io.IOException;

/**
 * 编辑器服务类
 *
 * @author xiayouxue
 * @date 2014/4/1 20:23
 */
public interface EditorService {

    /**
     * 打开编辑器
     */
    public String open(Editor editor) throws IOException;

}
