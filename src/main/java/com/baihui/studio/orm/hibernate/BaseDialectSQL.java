package com.baihui.studio.orm.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;

/**
 * 根据数据库方言获取SQL工具类。如果需要根据不同方言定义不同的SQL，需要
 * 创建本类的子类，并按需求覆写不同的方法。最后调用时通过getDefinedDialectSQL
 * 方法自动获取当前数据库方言对应的SQL语句。
 */
public class BaseDialectSQL {

    /**
     * 根据方言获取定义的SQL语句
     *
     * @return
     */
    public final String getDefinedDialectSQL(Dialect dialect) {
        String name = dialect.getClass().getName();
        if (name.contains("Oracle")) {
            return getOracleSQLString();
        } else if (name.contains("MySQL")) {
            return getMySQLSQLString();
        } else if (name.contains("SQLServer")) {
            return getSQLServerSQLString();
        } else if (name.contains("Sybase")) {
            return getSybaseSQLString();
        } else if (name.contains("DB2")) {
            return getDB2SQLString();
        } else if (name.contains("Derby")) {
            return getDerbySQLString();
        } else {
            return null;
        }
    }

    /**
     * 获取Oracle方言SQL语句
     *
     * @return
     */
    protected String getOracleSQLString() {
        throw new HibernateException("Oracle SQL String not defined.");
    }

    /**
     * 获取MySQL方言SQL语句
     *
     * @return
     */
    protected String getMySQLSQLString() {
        throw new HibernateException("MySQL SQL String not defined.");
    }

    /**
     * 获取SQLServer方言SQL语句
     *
     * @return
     */
    protected String getSQLServerSQLString() {
        throw new HibernateException("SQLServer SQL String not defined.");
    }

    /**
     * 获取Sybase方言SQL语句
     *
     * @return
     */
    protected String getSybaseSQLString() {
        throw new HibernateException("Sybase SQL String not defined.");
    }

    /**
     * 获取DB2方言SQL语句
     *
     * @return
     */
    protected String getDB2SQLString() {
        throw new HibernateException("DB2 SQL String not defined.");
    }

    /**
     * 获取Derby方言SQL语句
     *
     * @return
     */
    protected String getDerbySQLString() {
        throw new HibernateException("Derby SQL String not defined.");
    }
}
