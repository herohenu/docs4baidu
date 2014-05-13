package com.baihui.docs4baidu.fileview.service;

import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.docs4baidu.fileview.repository.FileviewDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class FileviewService {

    @Resource
    private FileviewDao fileviewDao;

    public List<Fileview> findAll(){
        return fileviewDao.getAll();
    }

    public Fileview get(Long id) {
        return fileviewDao.get(id);
    }

    public void save(Fileview fileview) {
        fileviewDao.save(fileview);
    }

    public void delete(Long id) {
        fileviewDao.delete(id);
    }

}
