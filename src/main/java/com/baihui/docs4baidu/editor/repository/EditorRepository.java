package com.baihui.docs4baidu.editor.repository;

import com.baihui.docs4baidu.editor.entity.Editor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author xiayouxue
 * @date 2014/3/31 20:23
 */
@Repository
public class EditorRepository {

    @Resource
    private Set<Editor> editors;

    public Set<Editor> getEditors() {
        return editors;
    }

    public void setEditors(Set<Editor> editors) {
        this.editors = editors;
    }

}
