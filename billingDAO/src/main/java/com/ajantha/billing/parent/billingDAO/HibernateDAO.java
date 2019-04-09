package com.ajantha.billing.parent.billingDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;

public class HibernateDAO
{
	protected Session session;

	protected String dialect;

	private Log log = LogFactory.getLog(HibernateDAO.class);

	public HibernateDAO(Session session) {
		this.session = session;
	}

	public HibernateDAO(Session session, String dialect) {
		this.session = session;
		this.dialect = dialect;
	}

	public void attachClean(Object instance) {
		log.debug("attaching clean Object instance");
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachDirty(Object instance) {
		log.debug("attaching dirty Object instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.debug("deleting Object instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object findById(long id, Class runtimeClass) {
		log.debug("findById Object instance with id: " + id);
		try {
			Object instance = (Object) session.get(runtimeClass, new Long(id));
			if (instance == null) {
				log.debug("findById successful, no instance found");
			} else {
				log.debug("findById successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		log.debug("merging Object instance");
		try {
			Object result = (Object) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void persist(Object transientInstance) {
		log.debug("persisting Object instance");
		try {
			session.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public int getPaginationCount(Criteria criteria,String projectionName){
		try{

			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			criteria.setProjection(Projections.countDistinct(projectionName));

			Iterator<CriteriaImpl.OrderEntry> orderIter = ((CriteriaImpl)criteria).iterateOrderings();
			List<Order> orderList = new ArrayList<>();
			while (orderIter.hasNext()) {
				CriteriaImpl.OrderEntry  oe = (CriteriaImpl.OrderEntry)orderIter.next();
				orderList.add(oe.getOrder());
			    orderIter.remove();
			}

			Number totalRowCount = (Number) criteria.uniqueResult();

			for(Order order : orderList){
				criteria.addOrder(order);
			}
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);

			return totalRowCount.intValue();

		} catch(RuntimeException re){
			log.error("getPaginationCount failed", re);
			throw re;
		}
	}

}
