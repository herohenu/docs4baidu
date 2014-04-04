package com.baihui.docs4baidu.editor.service.impl;

import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.service.EditorService;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author xiayouxue
 * @date 2014/4/1 13:37
 */
public class BaihuiEditorService implements EditorService {

    private EditorService editorService;

    public BaihuiEditorService(EditorService editorService) {
        this.editorService = editorService;
    }

    /**
     * 权限认证（百会）
     * 入参：合作单位
     * 出参：是否通过
     * 通过->编辑器，未通过->无权限界面
     */
    public String authorityFilterBaihui() {
        return null;
    }

    @Override
    public String open(Editor editor) throws IOException {
        return editorService.open(editor);
    }
}
