package com.baihui.editor.repository;

import com.baihui.editor.entity.Editor;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author xiayouxue
 * @date 2014/3/31
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
