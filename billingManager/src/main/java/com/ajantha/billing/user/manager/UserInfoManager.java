package com.ajantha.billing.user.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.ajantha.billing.parent.billingManager.HibernateManager;
import com.ajantha.billing.user.dao.UserInfoDAO;
import com.ajantha.billing.user.info.to.UserInfoTO;


/**
 * 
 *
 * @author Ragunathan J
 * @since T-portal 5.1.0
 */

public class UserInfoManager extends HibernateManager
{

	private Log log = LogFactory.getLog(UserInfoManager.class);

	public UserInfoManager() {
		//Suppress
	}

	public UserInfoTO persistUserInfo(UserInfoTO userInfoTO) throws Exception {
		Session session = null;
		try {
			session = initTransaction();
			UserInfoDAO userInfoDAO = new UserInfoDAO(session);
			userInfoDAO.persist(userInfoTO);
			commitTransaction(session);
			return userInfoTO;
		} catch (Exception e) {
			rollBackTransaction(session);
			log.error("Error occured while Persisting User Info", e);
			throw e;
		} finally {
			closeSession(session);
		}
	}

	/**
	 * Method to get user details by User Id
	 * 
	 * @param userObject
	 * 
	 * @return UserInfoTO
	 * @throws Exception
	 * 
	 * @author Sany Jones R
	 * @since T-portal 5.1.0
	 */
	public UserInfoTO getUserById(long userId) throws Exception {
		log.debug(" Entering getUserById in UserInfoManager...");
		Session session = null;
		try {
			session = initSession();
			return new UserInfoDAO(session).getUserById(userId);
		} catch (Exception e) {
			log.error("Error occured while getUserById of UserInfoManager", e);
			throw e;
		} finally {
			closeSession(session);
		}
	}
}
