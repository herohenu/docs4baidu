/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.baihui.docs4baidu.fileview.repository;

import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.studio.BaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class FileviewDao extends BaseDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Fileview> find() throws SQLException {
        List<Fileview> fileviews = new ArrayList<Fileview>();
        String sql = "select ID, PATH, SIZE, TYPE, ACCESSTOKEN, CREATE_TIME from FILEVIEW order by create_time desc";
        logger.info("sql:[" + sql + "]");
        try {
            rs = super.sqlQuery(sql);
            while (rs.next()) {
                Fileview fileview = new Fileview();
                fileview.setId(rs.getLong("ID"));
                fileview.setPath(rs.getString("PATH"));
                fileview.setSize(rs.getLong("SIZE"));
                fileview.setType(rs.getString("TYPE"));
                fileview.setAccesstoken(rs.getString("ACCESSTOKEN"));
                fileview.setCreateTime(rs.getDate("CREATE_TIME"));
                fileviews.add(fileview);
            }
        } catch (SQLException e) {
            logger.error(" SQLException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            closeAll();
            logger.info("method end!");
        }
        return fileviews;
    }

    public int insert(Fileview fileview) throws Exception {
        String sql = "INSERT INTO FILEVIEW (PATH, SIZE, TYPE, ACCESSTOKEN, CREATE_TIME) VALUES(?,?,?,?,?)";
        log.info("sql:[" + sql + "]");
        int result = super.sqlExecute(sql, fileview.getPath(), fileview.getSize(), fileview.getType(), fileview.getAccesstoken(), fileview.getCreateTime());
        log.info("result:[" + result + "]");
        return result;
    }
}
