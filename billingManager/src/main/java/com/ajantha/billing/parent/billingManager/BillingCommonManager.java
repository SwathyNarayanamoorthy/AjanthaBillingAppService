package com.ajantha.billing.parent.billingManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.ajantha.billing.parent.billingDAO.BillingCommonDAO;

public class BillingCommonManager extends HibernateManager
{
	private Statement statement = null;

	private Connection connection = null;
	
	private boolean closeConnection =true;

	public BillingCommonManager(long tenantId) {
		try {
			//List<String> propertyName = new ArrayList<>();
			/*propertyName.add(TenantPortalConstants.TenantInfoConstants.DRIVER_CLASS);
			propertyName.add(TenantPortalConstants.TenantInfoConstants.CONNECTION_URL);
			propertyName.add(TenantPortalConstants.TenantInfoConstants.USERNAME);
			propertyName.add(TenantPortalConstants.TenantInfoConstants.PASSWORD);
			propertyName.add(TenantPortalConstants.TenantInfoConstants.PASSWORD_ENCRYPTION_KEY);
			TenantPropertyValueManager propertyValueManager = new TenantPropertyValueManager();
			Map<String, TenantPropertyValueTO> propertyValue = propertyValueManager.getTenantPropertyValueMap(propertyName, tenantId);
			Class.forName(propertyValue.get(TenantPortalConstants.TenantInfoConstants.DRIVER_CLASS).getPropertyValue());
			String hibernateUrl = propertyValue.get(TenantPortalConstants.TenantInfoConstants.CONNECTION_URL).getPropertyValue();
			String encryptionKey = propertyValue.get(TenantPortalConstants.TenantInfoConstants.PASSWORD_ENCRYPTION_KEY).getPropertyValue();
			String userName = propertyValue.get(TenantPortalConstants.TenantInfoConstants.USERNAME).getPropertyValue();
			String password = FusionPasswordGeneration.decryptAES(propertyValue.get(TenantPortalConstants.TenantInfoConstants.PASSWORD).getPropertyValue(), encryptionKey);*/
			connection = DriverManager.getConnection("hibernateUrl", "userName", "password");
			statement = connection.createStatement();
			closeConnection =true;
		} catch (Exception e) {
			System.err.println("Error While Getting Commection statement for Fusion DB"+e);
		}
	}

	public BillingCommonManager(Connection conn) {
		try {
			connection = conn;
			statement = connection.createStatement();
			closeConnection =false;
		} catch (SQLException e) {
			System.err.println("Error occurred in Creating Connetion"+ e);
		}
	}
	public BillingCommonManager(){
		//Suppress
	}

	public Map<String, Boolean> getModuleInfoFromFusion(String query) throws Exception, SQLException {
		try {
			if (connection == null || statement == null) {
				throw new Exception("Cannot Connect To Fusion");
			}
			//BillingCommonDAO tenantPortalCommonDAO = new BillingCommonDAO(statement);
			BillingCommonDAO tenantPortalCommonDAO = null;
			Map<String, Boolean> userInfoMap = new HashMap<>();
			ResultSet result = tenantPortalCommonDAO.getModuleInfoFromFusion(query);
			while (result.next()) {
				String moduleName = result.getString("Fusion_Module_Name");
				boolean active = result.getBoolean("Active");
				userInfoMap.put(moduleName, active);
			}
			return userInfoMap;
		} catch (Exception e) {
			System.err.println("Error While getting Module Info from Fusion"+e);
			throw new Exception(e);
		} finally {
			System.out.println(" Leaving getModuleInfoFromFusion ");
			if(closeConnection && connection != null){
				connection.close();
			}
		}
	}

	public Map<String, String> getInfoFromFusion(String query) throws Exception, SQLException {
		try {
			if (connection == null || statement == null) {
				throw new Exception("Cannot Connect To Fusion");
			}
			//BillingCommonDAO tenantPortalCommonDAO = new BillingCommonDAO(statement);
			BillingCommonDAO tenantPortalCommonDAO = null;
			Map<String, String> userInfoMap = new HashMap<>();
			ResultSet result = tenantPortalCommonDAO.getModuleInfoFromFusion(query);
			while (result.next()) {
				String proeprtyName = result.getString("Property_Name");
				String propertyValue = result.getString("Property_Value");
				userInfoMap.put(proeprtyName, propertyValue);
			}
			return userInfoMap;
		} catch (Exception e) {
			System.err.println("Error While getting Info from Fusion"+e);
			throw new Exception(e);
		} finally {
			System.out.println(" Leaving getInfoFromFusion ");
			if(closeConnection && connection != null){
				connection.close();
			}
		}
	}
	
	public boolean getFusionModuleEnabledInfo(String query,boolean isReviewCheck) throws Exception, SQLException {
		try {
			if (connection == null || statement == null) {
				throw new Exception("Cannot Connect To Fusion");
			}
			//BillingCommonDAO tenantPortalCommonDAO = new BillingCommonDAO(statement);
			BillingCommonDAO tenantPortalCommonDAO = null;
			ResultSet result = tenantPortalCommonDAO.getModuleInfoFromFusion(query);
			System.out.println("result :" + result);
			boolean isActive = false;
			while (result.next()) {
				if(isReviewCheck){
					isActive = result.getBoolean("Access_Module_Active");
					System.out.println("Review isActive :" + isActive);
				}
				else{
					isActive = result.getBoolean("Access_Group_Active");
					System.out.println("isActive :" + isActive);
				}
			}
			return isActive;
		} catch (Exception e) {
			System.err.println("Error While getting Info from Fusion"+e);
			throw new Exception(e);
		} finally {
			System.out.println(" Leaving getInfoFromFusion ");
			if(closeConnection && connection != null){
				connection.close();
			}
		}
	}
}
