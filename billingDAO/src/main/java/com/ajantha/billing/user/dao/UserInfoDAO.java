package com.ajantha.billing.user.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ajantha.billing.parent.billingDAO.HibernateDAO;
import com.ajantha.billing.parent.billingDAO.TenantCriteria;
import com.ajantha.billing.user.info.to.UserInfoTO;


public class UserInfoDAO extends HibernateDAO
{

	private Log log = LogFactory.getLog(UserInfoDAO.class);

	public UserInfoDAO(Session session) {
		super(session);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfoTO getUserById(long userId) {
		log.debug(" Entering getUserById in UserInfoDAO...");
		try {
			Criteria userCriteria = session.createCriteria(UserInfoTO.class);
			userCriteria.add(Restrictions.eq(TenantCriteria.UserInfoConstants.USER_ID, userId));
			return (UserInfoTO) userCriteria.uniqueResult();
		} catch (Exception e) {
			log.error("Error occurred in getUserById of UserInfoDAO", e);
			throw e;
		}
	}
}