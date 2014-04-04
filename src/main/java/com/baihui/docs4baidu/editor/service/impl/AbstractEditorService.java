package com.baihui.docs4baidu.editor.service.impl;

import com.baihui.docs4baidu.editor.entity.Editor;
import com.baihui.docs4baidu.editor.repository.EditorRepository;
import com.baihui.docs4baidu.editor.service.EditorService;
import com.baihui.studio.service.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author xiayouxue
 * @date 2014/4/1 20:44
 */
public abstract class AbstractEditorService implements EditorService {

    @Resource
    private EditorRepository editorRepository;

    public Editor findEditorByTypeExtension(String type, String extension) {
        Set<Editor> editors = editorRepository.getEditors();
        for (Editor editor : editors) {
            if (editor.getCom().equals(type) && editor.getFormat().contains(extension)) {
                return editor;
            }
        }
        throw new ServiceException("没有找到类型{0}、扩展名“{1}”对应的编辑器配置！".replace("{0}", type).replace("{1}", extension));
    }
}
