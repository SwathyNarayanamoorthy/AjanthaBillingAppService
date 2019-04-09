/**
 * 
 */
package com.ajantha.billing.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

import com.ajantha.billing.common.constants.WebServiceConstants;
import com.ajantha.billing.parent.billingAPI.APIResourceUtil;

/**
 * 
 * @author sivabharani
 *
 */
@Path(WebServiceConstants.BILLING_BASE_URL + "/" + WebServiceConstants.BILLING_API_CURRENT_VERSION + "/user")
public class UserInfoResource
{

	private Log log = LogFactory.getLog(UserInfoResource.class);

	public UserInfoResource(@Context HttpServletRequest request, @Context HttpServletResponse response, @Context UriInfo uriInfo) throws Exception {
	}

	/**
	 * Method to create User
	 * 
	 * @request.representation.mediaType application/json
	 * @request.representation.example {"COMPANY_ID":"1","USER_NAME" : "a","PASSWORD" : "Exterro-123","FIRST_NAME" : "Arun","MIDDLE_NAME" : "","LAST_NAME" : "Prasadh","JOB_TITLE" : "a","EMAIL" : "a@exterro.com","ACTIVE" : "true"}
	 * @response.representation.200.mediaType application/json 
	 * @response.representation.200.example {"created_id":"2","message":"New user added successfully"}
	 * @response.representation.200.doc If successfully created then return the JSON as shown in example
	 * @response.representation.500.mediaType application/json 
	 * @response.representation.500.example { "error":"Dynamic Exception class error message" }
	 * @response.representation.500.doc Return error if any exception occurs
	 * @response.representation.412.mediaType application/json
	 * @response.representation.412.doc If required fields are missing then the exception is thrown
	 * 
	 * @param request
	 * @param jsonData
	 *            represents entire company details for example {username,companyId,password,email}
	 * @return application/json
	 * @author amohan
	 * @since T-portal 5.1.0
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@Context HttpServletRequest request, JSONObject jsonObject) {
		if (log.isDebugEnabled()) {
			log.debug("Entering into createUser method..");
		}
		JSONObject responseJSON = new JSONObject();
		try {
			UserInfoResourceHelper userInfoResourceHelper = new UserInfoResourceHelper();
			responseJSON = userInfoResourceHelper.createUser(request, jsonObject);
			return APIResourceUtil.buildGetResponse(responseJSON);
		} catch (Exception e) {
			log.error("Exception occured while creating user", e);
			return APIResourceUtil.buildErrorReponse(e);
		}
	}

	/**
	 * REST API to get user details
	 * 
	 * @externalapi
	 * 
	 * @response.representation.200.mediaType application/json 
	 * @response.representation.200.example {"CREATED_BY":"1","LAST_UPDATED_BY":"1","ACTIVE":"true","JOB_TITLE":"Dev","USER_ID":"1","USER_NAME":"s","LAST_NAME":"Jones","EMAIL":"sany@devmail.com","FIRST_NAME":"Sany","CREATED_DATE_TS":"2017-06-05 15:55:18.237","LAST_UPDATED_DATETS":"2017-06-05 15:55:18.237","MIDDLE_NAME":"","COMPANY_INFO_TO":{"ZIP":"1234","CREATED_DATETS":"2017-06-05 15:54:31.66","COMPANY_TYPE":"Corporate","ACTIVE":"true","COMPANY_DESC":"Exterro R & D","LASTUPDATED_DATETS":"2017-06-05 15:54:31.66"
	 *                                        ,"STATE":"CBE","USER_INFOBY_LASTUPDATEDBY":{},"COMPANY_ADDRESS":"CBE","COMPANY_NAME":"Exterro","USER_INFOBY_CREATEDBY":{},"COMPANY_URL":"Exterro.Com","CITY":"CBE","COUNTRY":"cnty","LICENCED_COMPNAY":"true","COMPANY_ID":"1","COMPANY_DNS":"localhost"}}
	 * @response.representation.200.doc It returns the JSON as shown in example
	 * 
	 * @response.representation.500.mediaType application/json 
	 * @response.representation.500.example {"error":"User not found for Id 2"} ### { "error":"dynamic exception message" }
	 * @response.representation.500.doc Returns error if any exception occurs
	 * 
	 * @response.representation.401.mediaType application/json 
	 * @response.representation.401.example { User is not authenticated. Can't access the resource }
	 * @response.representation.401.doc If the operation fails then returns the json object shown in example
	 * 
	 * @param request
	 * @param userId
	 *            {required} - Represent user id
	 * 
	 * @return javax.ws.rs.core.Response
	 * 
	 * @author Sany Jones R
	 * @since T-portal 5.1.0
	 */
	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context HttpServletRequest request, @PathParam("userId") long userId) {
		try {
			return APIResourceUtil.buildGetResponse(new UserInfoResourceHelper().getUserById(request, userId));
		} catch (Exception e) {
			log.error("Exception occured while getting user details by user id", e);
			return APIResourceUtil.buildErrorReponse(e);
		} finally {
			log.info("Exiting from getUser of UserInfoResource ");
		}
	}
}
