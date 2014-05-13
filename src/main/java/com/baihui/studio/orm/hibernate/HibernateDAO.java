package com.baihui.studio.orm.hibernate;

import com.baihui.studio.orm.BasicDAO;
import com.baihui.studio.utils.PropertyFilter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.dialect.Dialect;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 定义了Hibernate中经常使用的方法
 *
 * @param <T>  实体类
 * @param <PK> 该实体类的主键
 */
public interface HibernateDAO<T, PK extends Serializable> extends BasicDAO<T, PK> {

    /**
     * 获取当前会话
     *
     * @return
     */
    public Session getSession();

    /**
     * 根据分页条件查询
     *
     * @param page
     * @return
     */
    public HibernatePage<T> getAll(final HibernatePage<T> page);

    /**
     * 根据查询语句和条件创建一个查询
     *
     * @param queryString
     * @param values
     * @return
     */
    public Query createQuery(final String queryString,
                             final Object... values);

    /**
     * 根据查询语句创建一个查询
     *
     * @param queryString
     * @return
     */
    public Query createQuery(final String queryString);

    /**
     * 根据查询语句和条件创建一个查询
     *
     * @param queryString
     * @param values
     * @return
     */
    public Query createQuery(final String queryString,
                             final Map<String, ?> values);

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions);

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions);

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions);

    /**
     * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query);

    /**
     * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria);


    /**
     * 按HQL分页查询.
     *
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql  hql语句.
     *             无占位符及参数，如 hql = "from S s where t.status=1"
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql);

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql,
                                     final Object... values);

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 命名参数,按名称绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql,
                                     final Map<String, ?> values);

    /**
     * 根据Criterion进行分页查询
     *
     * @param page
     * @param criterions
     * @return
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page,
                                     final Criterion... criterions);

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    public Query setPageParameterToQuery(final Query q,
                                         final HibernatePage<T> page);

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    public Criteria setPageParameterToCriteria(final Criteria c,
                                               final HibernatePage<T> page);

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql,
                               final Object... values);

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql,
                               final Map<String, ?> values);

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */
    public long countCriteriaResult(final Criteria c);

    /**
     * 按属性查找对象列表,支持多种匹配方式.
     *
     * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
     */
    public List<T> findBy(final String propertyName,
                          final Object value, final PropertyFilter.MatchType matchType);

    /**
     * 按属性过滤条件列表查找对象列表.
     */
    public List<T> find(List<PropertyFilter> filters);

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page,
                                     final List<PropertyFilter> filters);

    /**
     * 按属性条件参数创建Criterion,辅助函数
     *
     * @param propertyName
     * @param propertyValue
     * @param matchType
     * @return
     */
    public Criterion buildCriterion(final String propertyName, final Object propertyValue, final PropertyFilter.MatchType matchType);

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */
    public Criterion[] buildCriterionByPropertyFilter(
            final List<PropertyFilter> filters);

    /**
     * 获取数据库方言
     */
    public Dialect getDialect();


}