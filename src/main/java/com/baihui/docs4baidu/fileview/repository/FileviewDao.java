/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.baihui.docs4baidu.fileview.repository;

import com.baihui.docs4baidu.fileview.entity.Fileview;
import com.baihui.studio.orm.hibernate.HibernateDAOImpl;
import org.springframework.stereotype.Repository;


@Repository
public class FileviewDao extends HibernateDAOImpl<Fileview, Long> {

}
