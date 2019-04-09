/**
 * 
 */
package com.ajantha.billing.resource;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

import com.ajantha.billing.parent.billingAPI.RestHelperResource;
import com.ajantha.billing.user.info.to.UserInfoTO;
import com.ajantha.billing.user.manager.UserInfoManager;

/**
 * @author amohan
 *
 */
public class UserInfoResourceHelper extends RestHelperResource
{

	private Log log = LogFactory.getLog(UserInfoResourceHelper.class);

	/**
	 * Method to create user
	 * 
	 * @param currentUser
	 * @param jsonObject
	 * @return created user id
	 * @throws Exception
	 * @author amohan
	 * @since T-portal 5.1.0
	 */

	public JSONObject createUser(HttpServletRequest request, JSONObject jsonObject) throws Exception {
		log.info("Entering into create User method in UserInfoResourceHelper");
		JSONObject jsonResult = new JSONObject();

		UserInfoManager userInfoManager = new UserInfoManager();
		try {
			UserInfoTO userInfoTO;
			userInfoTO = parseUserInfo(jsonObject);
			String currentPassword = userInfoTO.getUserName() + "_*&^&*_" + userInfoTO.getPassword();

			userInfoTO.setPassword(currentPassword);
			UserInfoTO createdUserTO = userInfoManager.persistUserInfo(userInfoTO);
			
			jsonResult.put("created_id", createdUserTO.getUserId());
			jsonResult.put("message", "New User created");

		} catch (Exception e) {
			log.error("Error while saving user details: ", e);
			throw new Exception(e);
		} finally {
			log.info("Exiting from createUser method in UserInfoResourceHelper");
		}

		return jsonResult;
	}

	/**
	 * Method to parse User json data.
	 * 
	 * @param currentUser
	 * @param jsonObject
	 * @return parsed UserInfoTO
	 * @throws Exception
	 * @author amohan
	 * @since T-portal 5.1.0
	 */
	private UserInfoTO parseUserInfo(JSONObject jsonObject) throws Exception {
		UserInfoTO parseUserInfoTO = null;
		log.info("Entering into parse UserInfo method.");
		try {
			parseUserInfoTO = new UserInfoTO();
			final List<String> requiredFieldsForUser = new ArrayList<>();
			{
				requiredFieldsForUser.add("USER_NAME");
				requiredFieldsForUser.add("PASSWORD");
				requiredFieldsForUser.add("PHONE_NUMBER");
			}
			for (String requiredField : requiredFieldsForUser) {
				if (!jsonObject.has(requiredField) || jsonObject.getString(requiredField).isEmpty()) {
					log.info("Missing mandatory field " + requiredField);
					throw new Exception("Missing mandatory field " + requiredField);
				}
			}
			if(!jsonObject.getString("PASSWORD").contentEquals(jsonObject.getString("VERIFY_PASSWORD"))){
				throw new Exception("Passwords do not match. Please try again");
			}
			
			parseUserInfoTO.setUserName(jsonObject.has("USER_NAME") ? jsonObject.getString("USER_NAME").trim() : "");
			parseUserInfoTO.setPassword(jsonObject.has("PASSWORD") ? jsonObject.getString("PASSWORD") : "");
			parseUserInfoTO.setPhoneNumber(jsonObject.getString("PHONE_NUMBER"));
		} catch (Exception ex) {
			log.error("Error parsing JSON", ex);
			throw new Exception(ex);
		} finally {
			log.info("Exiting from user parse JSON");
		}
		return parseUserInfoTO;
	}

	/**
	 * Method to get user details by User Id
	 * 
	 * @param userId
	 * 
	 * @return JSONObject
	 * @throws Exception
	 * 
	 * @author Sany Jones R
	 * @since T-portal 5.1.0
	 */
	public JSONObject getUserById(HttpServletRequest request, long userId) throws Exception {
		log.info("Entering into getUserById method in UserInfoResourceHelper");
		JSONObject userInfoJSON = new JSONObject();
		UserInfoTO userInfoTO = null;
		try {
			UserInfoManager userInfoManager =new UserInfoManager();
			userInfoTO = userInfoManager.getUserById(userId);
			if (userInfoTO != null) {
				userInfoJSON = buildJsonObject(userInfoTO);
			} else {
				throw new Exception("User not found for Id " + userId);
			}
		} catch (Exception e) {
			log.error("Error occured while getting user info of UserInfoResourceHelper", e);
			throw new Exception(e);
		} finally {
			log.info("Exiting from getUserById method in UserInfoResourceHelper");
		}
		return userInfoJSON;
	}
}
