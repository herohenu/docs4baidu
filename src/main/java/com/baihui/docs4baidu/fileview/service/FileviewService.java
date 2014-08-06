package com.baihui.docs4baidu.fileview.service;

import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.docs4baidu.fileview.repository.FileviewDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
public class FileviewService {

    @Resource
    private FileviewDao fileviewDao;

    public List<Fileview> find() throws SQLException {
        return fileviewDao.find();
    }

    public void save(Fileview fileview) throws Exception {
        fileviewDao.insert(fileview);
    }


}
