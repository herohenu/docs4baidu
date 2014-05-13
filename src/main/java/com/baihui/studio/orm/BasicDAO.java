package com.baihui.studio.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础数据访问接口，该接口定义了常见的实体类访问方法，适合所有ORM框架。
 *
 * @param <T>  实体类
 * @param <PK> 该实体类的主键
 * @modify by xiayouxue 14-5-8 新增"集合删除"

 */
public interface BasicDAO<T, PK extends Serializable> {

    /**
     * 保存一个实体到数据库
     *
     * @param entity
     * @return
     */
    public T save(final T entity);

    /**
     * 更新一个实体对应的数据库记录
     *
     * @param entity
     * @return
     */
    public T update(T entity);

    /**
     * 合并实体到数据库
     *
     * @param entity
     * @return
     */
    public T merge(T entity);

    /**
     * 删除一个实体对应的数据库记录
     *
     * @param entity
     */
    public void delete(final T entity);

    /**
     * 根据实体ID从删除该实体对应的记录
     *
     * @param id
     */
    public void delete(final PK id);

    /**
     * 集合删除
     *
     * @param ids
     */
    public void delete(final PK... ids);

    /**
     * 根据主键从数据库中获取一个实体
     *
     * @param id
     * @return
     */
    public T get(final PK id);

    /**
     * 根据主键的集合从数据库获取实体的集合
     *
     * @param ids
     * @return
     */
    public List<T> get(final Collection<PK> ids);

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<T> getAll();

    /**
     * 按条件查询所有记录
     *
     * @param orderByProperty 排序条件
     * @param isAsc           是否升序
     * @return
     */
    public List<T> getAll(String orderByProperty, boolean isAsc);

    /**
     * 根据实体属性名称和值查询对应的实体
     *
     * @param propertyName
     * @param value
     * @return
     */
    public List<T> findBy(final String propertyName, final Object value);

    /**
     * 根据实体属性名称和值查询单一实体
     *
     * @param propertyName
     * @param value
     * @return
     */
    public T findUniqueBy(final String propertyName, final Object value);

    /**
     * 根据HQL语句和条件查询结果列表
     *
     * @param <X>
     * @param hql
     * @param values
     * @return
     */
    public <X> List<X> find(final String hql, final Object... values);

    /**
     * 根据HQL语句和条件查询结果列表
     *
     * @param <X>
     * @param hql
     * @param values
     * @return
     */
    public <X> List<X> find(final String hql,
                            final Map<String, ?> values);

    /**
     * 根据HQL语句查询单一结果
     *
     * @param <X>
     * @param hql
     * @return
     */
    public <X> X findUnique(final String hql);

    /**
     * 根据HQL语句和条件查询单一结果
     *
     * @param <X>
     * @param hql
     * @param values
     * @return
     */
    public <X> X findUnique(final String hql, final Object... values);

    /**
     * 根据HQL语句和条件查询单一结果
     *
     * @param <X>
     * @param hql
     * @param values
     * @return
     */
    public <X> X findUnique(final String hql,
                            final Map<String, ?> values);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param values
     * @return
     */
    public int batchExecute(final String hql, final Object... values);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param values
     * @return
     */
    public int batchExecute(final String hql,
                            final Map<String, ?> values);

    /**
     * 初始化代理对象
     *
     * @param proxy
     */
    public void initProxyObject(Object proxy);

    /**
     * 强制flush数据到数据库
     */
    public void flush();

    /**
     * 获取主键的名称
     *
     * @return
     */
    public String getIdName();

    /**
     * 查询属性值是否唯一
     *
     * @param propertyName
     * @param newValue
     * @param oldValue
     * @return
     */
    public boolean isPropertyUnique(final String propertyName,
                                    final Object newValue, final Object oldValue);

}