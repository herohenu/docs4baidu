package com.baihui.studio.orm.hibernate;

import com.baihui.studio.utils.PropertyFilter;
import com.baihui.studio.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 扩展功能包括分页查询,按属性过滤条件列表查询
 *
 * @param <T>  DAO操作的对象类型
 * @param <PK> 主键类型
 * @modify by xiayouxue 14-5-8 新增方法"离线分页查询"
 */
@SuppressWarnings("unchecked")
public class HibernateDAOImpl<T, PK extends Serializable> implements
        HibernateDAO<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * 用于Dao层子类的构造函数. 通过子类的泛型定义取得对象类型Class.
     * 例如：
     * public class UserDao extends HibernateDao<User, Long>{}
     */
    public HibernateDAOImpl() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 保存新增或修改的对象.
     */
    public T save(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: {" + entity + "}");
        return entity;
    }

    /**
     * 更新对象
     */
    public T update(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: {" + entity + "}");
        return entity;
    }

    /**
     * 合并对象
     */
    public T merge(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().merge(entity);
        logger.debug("save entity: {" + entity + "}");
        return entity;
    }

    /**
     * 删除对象.
     *
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        logger.debug("delete entity: {" + entity + "}");
    }

    /**
     * 按id删除对象.
     */
    public void delete(final PK id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        logger.debug("delete entity {" + entityClass.getSimpleName()
                + "},id is {" + id + "}");
    }

    /**
     * 按id删除对象.
     */
    public void delete(final PK... ids) {
        Assert.notNull(ids, "id不能为空");
        for (int i = 0; i < ids.length; i++) {
            delete(ids[i]);
        }
        logger.debug("delete entity {" + entityClass.getSimpleName()
                + "},id is {" + ids + "}");
    }

    /**
     * 按id获取对象.
     */
    public T get(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(final Collection<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return find();
    }

    /**
     * 获取全部对象, 支持按属性行序.
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按sql查询列表创建SQLQuery对象.
     */
    public SQLQuery createSQLQuery(String sql) {
        return this.getSession().createSQLQuery(sql);
    }

    /**
     * 根据查询SQL与参数列表创建SQLQuery对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public SQLQuery createSQLQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        SQLQuery query = getSession().createSQLQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    public <X> List<X> find(final DetachedCriteria criteria) {
        return (List<X>) criteria.getExecutableCriteria(getSession()).list();
    }

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    public <X> X findUnique(final DetachedCriteria criteria) {
        return (X) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    // form S s where s.status=1
    public <X> X findUnique(String hql) {
        return (X) createQuery(hql).uniqueResult();
    }

    public <X> X findSQLUnique(final String sql, final Object... values) {
        return (X) createSQLQuery(sql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public Query createQuery(final String queryString) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString,
                             final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        // criteria.setCacheable(true);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
     * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String propertyName,
                                    final Object newValue, final Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }
        Object object = (T) findUniqueBy(propertyName, newValue);
        return (object == null);
    }

    /**
     * 分页获取全部对象.
     */
    public HibernatePage<T> getAll(final HibernatePage<T> page) {
        return findPage(page);
    }

    public HibernatePage<T> findSQLPage(final HibernatePage<T> page, final String sql,
                                        final Object... values) {
        Assert.notNull(page, "page不能为空");

        Query q = createSQLQuery(sql, values);

        if (page.isAutoCount()) {
            long totalCount = countSqlResult(sql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql,
                                     final Object... values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 命名参数,按名称绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql,
                                     final Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    // hql = "from S s where t.status=1"

    /**
     * 按HQL分页查询.
     *
     * @param page 分页参数.
     * @param hql  hql语句. 无占位符及参数，如 hql = "from S s where t.status=1"
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final String hql) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按Criteria分页查询.
     *
     * @param page       分页参数.
     * @param criterions 数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final Criterion... criterions) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(criterions);

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(c, page);

        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 离线分页查询.
     *
     * @param page             分页参数.
     * @param detachedCriteria 离线查询条件.
     * @return 分页查询结果.附带结果列表及所有查询输入参数.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page, final DetachedCriteria detachedCriteria) {
        Assert.notNull(page, "page不能为空");

        Criteria c = detachedCriteria.getExecutableCriteria(getSession());

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(c, page);

        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    public Query setPageParameterToQuery(final Query q, final HibernatePage<T> page) {

        if (page.getHibernatePageSize() < 0) {
            page.setHibernatePageSize((int) page.getTotalCount());
        }
        //Assert.isTrue(page.getHibernatePageSize() > 0,
        //                "Page Size must larger than zero");

        // hibernate的firstResult的序号从0开始

        q.setFirstResult(page.getFirst() - 1);
        q.setMaxResults(page.getHibernatePageSize());
        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    public Criteria setPageParameterToCriteria(final Criteria c,
                                               final HibernatePage<T> page) {
        if (page.getHibernatePageSize() < 0) {
            page.setHibernatePageSize((int) page.getTotalCount());
        }
        Assert.isTrue(page.getHibernatePageSize() > 0,
                "Page Size must larger than zero");

        // hibernate的firstResult的序号从0开始
        c.setFirstResult(page.getFirst() - 1);
        c.setMaxResults(page.getHibernatePageSize());

        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getHibernateOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getHibernateOrder(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length,
                    "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (HibernatePage.ASC.equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql, final Object... values) {
        String countHql = prepareCountHql(hql);
        return (Long) findUnique(countHql, values);
    }

    public long countSqlResult(final String sql, final Object... values) {
        String countSql = prepareCountSql(sql);
        Number number = findSQLUnique(countSql, values);
        return number.longValue();
    }


    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql, final Map<String, ?> values) {
        String countHql = prepareCountHql(hql);
        return (Long) findUnique(countHql, values);
    }

    public long countHqlResult(final String hql) {
        String countHql = prepareCountHql(hql);
        return (Long) findUnique(countHql);
    }

    private String prepareCountHql(String orgHql) {
        String fromHql = orgHql;
        // select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");
        return "select count(*) " + fromHql;
    }

    private String prepareCountSql(String sql) {
        String fromHql = sql;
        // select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");
        return "select count(*) " + fromHql;
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */
    public long countCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries =
                (List<CriteriaImpl.OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");
        ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<CriteriaImpl.OrderEntry>());


        // 执行Count查询
        Long totalCountObject = (Long) c.setProjection(Projections.rowCount())
                .uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null)
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

        if (transformer != null)
            c.setResultTransformer(transformer);

        ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);

        return totalCount;
    }

    /**
     * 按属性查找对象列表,支持多种匹配方式.
     *
     * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
     */
    public List<T> findBy(final String propertyName, final Object value,
                          final PropertyFilter.MatchType matchType) {
        Criterion criterion = buildCriterion(propertyName, value, matchType);
        return find(criterion);
    }

    /**
     * 按属性过滤条件列表查找对象列表.
     */
    public List<T> find(List<PropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return find(criterions);
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public HibernatePage<T> findPage(final HibernatePage<T> page,
                                     final List<PropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return findPage(page, criterions);
    }

    /**
     * 按属性条件参数创建Criterion,辅助函数.
     */
    public Criterion buildCriterion(final String propertyName,
                                    final Object propertyValue, final PropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        // 根据MatchType构造criterion
        switch (matchType) {
            case EQ:
                criterion = Restrictions.eq(propertyName, propertyValue);
                break;
            case LIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue,
                        MatchMode.ANYWHERE);
                break;
            case LE:
                criterion = Restrictions.le(propertyName, propertyValue);
                break;
            case LT:
                criterion = Restrictions.lt(propertyName, propertyValue);
                break;
            case GE:
                criterion = Restrictions.ge(propertyName, propertyValue);
                break;
            case GT:
                criterion = Restrictions.gt(propertyName, propertyValue);
        }
        return criterion;
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */
    public Criterion[] buildCriterionByPropertyFilter(
            final List<PropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
                Criterion criterion = buildCriterion(filter.getPropertyName(),
                        filter.getMatchValue(), filter.getMatchType());
                criterionList.add(criterion);
            } else {// 包含多个属性需要比较的情况,进行or处理.
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildCriterion(param, filter
                            .getMatchValue(), filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }
        return criterionList.toArray(new Criterion[criterionList.size()]);
    }

    /**
     * 根据时间格式返回系统时间
     *
     * @param timeStyle
     * @return
     */
    public Date getSysTime(TimeStyle timeStyle) {
        SQLQuery query = getSession().createSQLQuery(getDialect().getCurrentTimestampSelectString());
        Date timeStamp = (Date) query.uniqueResult();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStamp);
        switch (timeStyle) {
            case DATE:
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.AM_PM, Calendar.AM);
                return calendar.getTime();
            case TIME:
                calendar.set(Calendar.MILLISECOND, 0);
                return calendar.getTime();
            case TIMESTAMP:
                return timeStamp;
            default:
                return timeStamp;
        }
    }

    /**
     * 时间格式
     *
     * @author Li Rui
     */
    public enum TimeStyle {
        DATE,
        TIME,
        TIMESTAMP;
    }

    /**
     * 获取数据库方言
     */
    public Dialect getDialect() {
        return ((SessionImpl) getSession()).getFactory().getDialect();
    }
}
