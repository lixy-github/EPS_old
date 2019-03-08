package com.broadcontact.common.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.broadcontact.common.entity.BaseEntity;

@Component
public class CommonDAO {
  private HibernateTemplate hibernateTemplate;

  public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
  }
	
  @Resource
  public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
  }

  public static Logger log = Logger.getLogger(CommonDAO.class);

  public List list(Class<? extends BaseEntity> objectClass) throws Exception {
    List list = null;
    try {
      list = hibernateTemplate.loadAll(objectClass);
    } catch (Exception e) {
      String msg = "[BCBS]:Occur error when load all entity.";
      log.error(msg);
      throw e;
    }
    return list;
  }
  
  public void update(BaseEntity entity) throws Exception {
	try {
		hibernateTemplate.update(entity);
	} catch (Exception e) {
		String msg = "[BCBS]:Occur error when update Entity " + entity.toString();
		log.error(msg);
		throw e;
	}
  }
  
  public BaseEntity save(BaseEntity entity) throws Exception {
    try {
    	hibernateTemplate.save(entity);
    } catch (Exception e) {
    	String msg = "[BCBS]:Occur error when insert Entity " + entity.toString();
    	log.error(msg);
    	throw e;
    }
    return entity;
  }
  
  public BaseEntity saveOrUpdate(BaseEntity entity) throws Exception {
	    try {
	    	hibernateTemplate.saveOrUpdate(entity);
	    } catch (Exception e) {
	    	String msg = "[BCBS]:Occur error when saveOrUpdate Entity " + entity.toString();
	    	log.error(msg);
	    	throw e;
	    }
	    return entity;
	  }

  public void delete(BaseEntity entity) throws Exception {
	try {
		hibernateTemplate.delete(entity);
	} catch (Exception e) {
		String msg = "[BCBS]:Occur error when delete Entity " + entity.toString();
		log.error(msg);
		throw e;
	}
  }

  public void delete(Class<? extends BaseEntity> objectClass, final Serializable id) throws Exception {
	try {
		BaseEntity entity = selectById(objectClass, id);
		hibernateTemplate.delete(entity);
	} catch (Exception e) {
		String msg = "[BCBS]:Occur error when delete Entity " + id.toString();
		log.error(msg);
		throw e;
	}
  }

  public void deleteAll(Collection<? extends BaseEntity> collection) throws Exception {
	for (BaseEntity entity:collection) {
		try {
			delete(entity);
		} catch (Exception e) {
			String msg = "[BCBS]:Occur error when delete all Entity";
			log.error(msg);
			throw e;
		}
	}
  }

  public BaseEntity selectById(Class<? extends BaseEntity> objectClass, final Serializable id) throws Exception {
	BaseEntity entity = null;
	try {
		entity = (BaseEntity) hibernateTemplate.load(objectClass, id);
	} catch (Exception e) {
		String msg = "[BCBS]:Occur error when delete Entity [" + objectClass
	      + "]" + id.toString();
		log.error(msg);
		throw e;
	}
	return entity;
  }
  
/**
  public List filter(Collection c, int start, int count, String filterStr) throws Exception {
  	if (count>0)
  		return session.createFilter(c,filterStr).setFirstResult(start).setMaxResults(count).list();
  	else
  		return session.createFilter(c,filterStr).setFirstResult(start).list();
  }

  public int count(Collection c,String q) throws Exception {
  	return ((Integer)session.createFilter(c,"select count(*) "+q).list().get(0)).intValue();
  }
**/
  
  public List query(DetachedCriteria dc) throws Exception {
    List list = null;
    try {
      list = hibernateTemplate.findByCriteria(dc);
    } catch (Exception e) {
      String msg = "[BCBS]:Occur error when query by detachedCriteria.";
      log.error(msg);
      throw e;
    }
    return list;
  }

  public List selectHql(String hql) throws Exception {
    List list = null;
    try {
      list = hibernateTemplate.find(hql);
    } catch (Exception e) {
      String msg = "[BCBS]:Occur error when select Entity through HQL: " + hql;
      log.error(msg);
      throw e;
    }
    return list;
  }
  
  public List selectSql(final String sql) throws Exception {
    List list = null;
    try {
        list = hibernateTemplate.executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {  
                SQLQuery query = session.createSQLQuery(sql);    
                return query.list();  
            }  
        });
    } catch (Exception e) {
      String msg = "[BCBS]:Occur error when select Entity through SQL: " + sql;
      log.error(msg);
      throw e;
    }
    return list;
  }

  public List findByField(final Class<? extends BaseEntity> className,final String field, final Object value) throws Exception{
	List list = null;
	try {
		list = hibernateTemplate.executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {  
                return session.createCriteria(className).add(Expression.eq(field,value)).list();
            }  
        });
	} catch (Exception e) {
		String msg = "[BCBS]:Occur error when find Entity by field-value : "
		    + field + " = " + value.toString();
		log.error(msg);
		throw e;
	}
	return list;
  }

  public PaginationSupport query(final DetachedCriteria dc, final int startIndex, final int pageSize,final String keyword) throws Exception {
	return (PaginationSupport) hibernateTemplate.execute(
			new HibernateCallback<PaginationSupport>() {
				public PaginationSupport doInHibernate(Session session)
						throws HibernateException {					
		        Criteria criteria = dc.getExecutableCriteria(session);
		        int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		        criteria.setProjection(null);
				criteria.setResultTransformer(Criteria.ROOT_ENTITY);
				List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items,count, pageSize, startIndex);
		        ps.setKeyword(keyword);
		        ps.setTotalCount(count);
		        ps.setAnchor("");
		        return ps;
			}
		});
  }
  public PaginationSupport query(final DetachedCriteria dc, final int startIndex, final int pageSize,final String keyword,final String anchor) throws Exception {
	return (PaginationSupport) hibernateTemplate.execute(
			new HibernateCallback<PaginationSupport>() {
				public PaginationSupport doInHibernate(Session session)
						throws HibernateException {					
		        Criteria criteria = dc.getExecutableCriteria(session);
		        int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		        criteria.setProjection(null);
				criteria.setResultTransformer(Criteria.ROOT_ENTITY);
				List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items,count, pageSize, startIndex);
		        ps.setKeyword(keyword);
		        ps.setTotalCount(count);
		        ps.setAnchor(anchor);
		        return ps;
			}
		});
  }
  
  public PageBean getPageBean(int currentPage, int pageSize, String hql,
			List<Object> parameters) {
		
		Query listQuery = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
		if(parameters!=null){
			for(int i = 0;i<parameters.size();i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		
		listQuery.setFirstResult((currentPage-1)*pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();
		
		
		Query countQuery = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("SELECT COUNT(*) "+hql);
		if(parameters!=null){
			for(int i = 0;i<parameters.size();i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();
		
		return new PageBean(currentPage, pageSize, count.intValue(), list);
	}
  
  public PaginationSupport query(final DetachedCriteria dc, List<Order> orders, final int startIndex, final int pageSize) throws Exception {
		return (PaginationSupport) hibernateTemplate.execute(
				new HibernateCallback<PaginationSupport>() {
					public PaginationSupport doInHibernate(Session session)
							throws HibernateException {
					PaginationSupport ps = new PaginationSupport(pageSize);
			        Criteria criteria = dc.getExecutableCriteria(session);
			        int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			        criteria.setProjection(null);
					criteria.setResultTransformer(Criteria.ROOT_ENTITY);
					List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
			        ps.setTotalCount(count);
			        ps.setItems(items);
			        return ps;
				}
			});
	  }
  
  
}