package com.ajantha.billing.parent.billingAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

public class APIResourceUtil
{
	private static Log log = LogFactory.getLog(APIResourceUtil.class);

	private APIResourceUtil() {
		
	}
	public static Response buildResponse(Status status, Object entity, String mediaType) {
		return Response.status(status).entity(entity).type(mediaType).header("X-Content-Type-Options", "nosniff").build();
	}

	public static Response buildResponse(Status status, JSONObject entity) {
		return buildResponse(status, entity, MediaType.APPLICATION_JSON);
	}

	public static Response buildResponse(Status status, JSONArray entity) {
		return buildResponse(status, entity, MediaType.APPLICATION_JSON);
	}

	public static Response buildGetResponse(JSONObject entity) {
		return buildResponse(Status.OK, entity);
	}

	public static Response buildGetResponse(JSONArray entity) {
		return buildResponse(Status.OK, entity);
	}

	public static Response buildPostResponse(JSONObject entity) {
		return buildResponse(Status.CREATED, entity);
	}

	public static Response buildGetResponse(Object entity) {
		JSONObject object = null;
		try {
			object = new JSONObject(new Gson().toJson(entity));
		} catch (JSONException e) {
			log.error("Error building get response", e);
		}
		return buildGetResponse(object);
	}

	public static Response buildDeleteResponse(String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("message", message);
		} catch (JSONException e) {
			log.error("Error building delete response", e);
		}
		return buildResponse(Status.OK, json);
	}

	public static Response buildPutResponse(String updatedId, String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("updated_id", updatedId);
			json.put("message", message);
		} catch (JSONException e) {
			log.error("Error building put response", e);
		}
		return buildResponse(Status.OK, json);
	}

	public static Response buildPostResponse(String createdId, String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("created_id", createdId);
			json.put("message", message);
		} catch (JSONException e) {
			log.error("Error building post response", e);
		}
		return buildResponse(Status.CREATED, json);
	}

	public static Response buildErrorReponse(Throwable t) {
		JSONObject json = new JSONObject();
		try {
			log.error("Exception occurred", t);

			json.put("error", t.getMessage());
		} catch (JSONException e) {
			log.error("Error building error response", e);
		}

		return buildResponse(Status.INTERNAL_SERVER_ERROR, json);
	}

	public static Response buildErrorMessageReponse(String t) {
		JSONObject json = new JSONObject();
		try {

			json.put("error", t);
		} catch (JSONException e) {
			log.error("Error building error response", e);
		}

		return buildResponse(Status.INTERNAL_SERVER_ERROR, json);
	}

	public static Response buildErrorReponse(List<String> missingFields) {
		JSONObject json = new JSONObject();
		try {
			log.error("Exception occured while validating the input fields");

			json.put("REQUIRED_FIELDS_MISSING", missingFields);
		} catch (JSONException e) {
			log.error("Error building error response", e);
		}

		return buildResponse(Status.PRECONDITION_FAILED, json);
	}

	public static Response buildPreConditionErrorReponse(String message) {
		JSONObject json = new JSONObject();
		try {
			log.error("Exception occured while validating the input fields");

			json.put("REQUIRED_FIELDS_MISSING", message);
		} catch (JSONException e) {
			log.error("Error building error response", e);
		}

		return buildResponse(Status.PRECONDITION_FAILED, json);
	}

	public static Response buildFileUploadSizeExceedErrorReponse(String message, long permittedSize, long actualSize) {
		JSONObject json = new JSONObject();
		try {
			json.put("ERROR_CODE", "FILE_SIZE_EXCEEDED");
			json.put("PERMITTED_SIZE", permittedSize);
			json.put("ACTUAL_SIZE", actualSize);
			json.put("error", message);
		} catch (JSONException e) {
			log.error("Error building error response", e);
		}
		return buildResponse(Status.NOT_ACCEPTABLE, json);
	}

	public static List<String> stringToList(String expand, String delimiter) {
		List<String> retval = new ArrayList<>();
		if (expand != null && !expand.isEmpty()) {
			String[] strings = expand.split(delimiter);
			for (String s : strings) {
				retval.add(s.toLowerCase());
			}
		}
		return retval;
	}

	public static List<String> prepareList(String commaSeparatedStr, String delimiter) {

		StringTokenizer tokenizer = new StringTokenizer(commaSeparatedStr, delimiter);
		List<String> list = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			list.add(tokenizer.nextToken());
		}

		return list;
	}

	public static Response buildUnAuthorizedErrorMessage() {
		return Response.status(Status.UNAUTHORIZED).entity("User is not authorized. Can't access the resource").build();
	}

}