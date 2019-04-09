package com.ajantha.billing.parent.billingDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 *
 * @author Ragunathan J
 * @since T-portal 5.1.0
 */
@SuppressWarnings("deprecation")
public class BillingCommonDAO extends HibernateDAO
{

	private Log log = LogFactory.getLog(BillingCommonDAO.class);

	private Statement statement =null;
	public BillingCommonDAO(Session session) {
		super(session);
	}

	/**
	 * Method to connect to Fusion DB for Feature Group Details
	 * 
	 * @param hibernateUrl
	 * @param userName
	 * @param password
	 * @param featureGrpName
	 * @throws Exception
	 * @author Ragunathan J
	 * @since T-portal 5.1.0
	 */
	
	public void connectToFusionDB(String hibernateUrl, String userName, String password, String featureGrpName) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DriverManager.getConnection(hibernateUrl, userName, password);
			stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE feature_group SET Enabled =0 WHERE Feature_Name = '" + featureGrpName + "'");
		} catch (Exception e) {
			log.error("Error While connecting to fusion", e);
		} finally {
			log.info(" Leaving connectToFusionDB ");
			if(connection != null) {
				connection.close();
				}
				if(stmt != null) {
				stmt.close();
				}
		}
	}

	/**
	 * Method to execute the Native SQL Queries
	 * 
	 * @param queries
	 * @throws Exception
	 * @author Ragunathan J
	 * @since T-portal 5.1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void executeQuery(Object queries) throws Exception {
		log.info("Entering into executeQuery of TenantPortalCommonDAO");
		try {
			List<String> updateQueries = new ArrayList<>();
			if (queries instanceof String) {
				updateQueries.add((String) queries);
			} else {
				updateQueries.addAll((List<String>) queries);
			}

			for (String updateQuery : updateQueries) {
				Query query = session.createSQLQuery(updateQuery);
				query.executeUpdate();
			}
		} catch (Exception ex) {
			log.error("Error occured while updating queries", ex);
			throw ex;
		}
	}

	/**
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 * @author Ragunathan J
	 * @since T-portal 5.1.0
	 */
	
	public ResultSet getModuleInfoFromFusion(String query) throws Exception {
		try {
			return statement.executeQuery(query);
		} catch (Exception e) {
			log.error("Error While connecting to fusion", e);
			throw e;
		} finally {
			log.info(" Leaving getModuleInfoFromFusion ");
		}
	}
}
