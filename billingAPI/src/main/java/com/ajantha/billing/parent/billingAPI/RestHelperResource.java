package com.ajantha.billing.parent.billingAPI;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

import com.ajantha.billing.parent.billingTO.BuilderAnotation;
import com.google.gson.Gson;

public abstract class RestHelperResource
{

	private static Log log = LogFactory.getLog(RestHelperResource.class);



	public int getTotalPageCountBySize(int size, int noOfRecords) {
		return (int) Math.ceil(size / Double.valueOf(String.valueOf(noOfRecords)));
	}

	/**
	 * Method to Build jsonObject For given Entity
	 * 
	 * @param entityType
	 * @return
	 * @throws Exception
	 * @author Ragunathan J
	 * @since T-portal 5.1.0
	 */
	public JSONObject buildJsonObject(Object entityType) throws Exception {
		try {
			if (entityType == null) {
				return new JSONObject();
			}
			return new JSONObject(new Gson().toJson(getJsonObject(entityType)));
		} catch (Exception e) {
			log.error("Exception Occrued While Getting JsonObject", e);
			throw e;
		}
	}

	public Map<String, Object> getJsonObject(Object entityObject) throws Exception {
		return getJsonObject(entityObject, false);
	}

	/**
	 * Method To get MAp for given Entity
	 * 
	 * @param entityObject
	 * @param isfromCollection
	 * @return
	 * @throws Exception
	 * @author Ragunathan J
	 * @since T-portal 5.1.0
	 */
	public Map<String, Object> getJsonObject(Object entityObject, boolean isfromCollection) throws Exception {
		Map<String, Object> complexMap = new HashMap<>();
		try {
			Field[] fields = entityObject.getClass().getDeclaredFields();
			boolean isJsonBuilt = true;
			for (Field f : fields) {
				f.setAccessible(true);
				BuilderAnotation jsonMapping = f.getAnnotation(BuilderAnotation.class);
				if (jsonMapping != null) {
					try {
						isJsonBuilt = false;
						Object object = f.get(entityObject);
						if (object != null) {
							if (f.getType().isPrimitive() || f.getType().isInstance(new String()) || String.class.isAssignableFrom(f.getType()) || Number.class.isAssignableFrom(f.getType())) {
								complexMap.put(jsonMapping.name(), String.valueOf(object));
								isJsonBuilt = true;
							} /*else if (Date.class.isAssignableFrom(f.getType())) {

								Date date = (Date) object;
								DateUtils dateUtils = new DateUtils();
								complexMap.put(jsonMapping.name(), dateUtils.formatDateToYYYYMMDDTHHMMSSSSSZ(date));
								isJsonBuilt = true;

							}*/ else if (!isfromCollection && Collection.class.isAssignableFrom(f.getType())) {
								Collection<?> collection = (Collection<?>) object;
								List<Map<String, Object>> onetoMany = new ArrayList<>();
								for (Object e : collection) {
									onetoMany.add(getJsonObject(e, true));
								}
								complexMap.put(jsonMapping.name(), onetoMany);
								isJsonBuilt = true;
							} else {
								if (!isfromCollection) {
									complexMap.put(jsonMapping.name(), getJsonObject(object, true));
									isJsonBuilt = true;
								} else {
									Field[] innerFields = object.getClass().getDeclaredFields();
									Map<String, Object> innerValuesMap = new HashMap<>();
									boolean isInnerJsonBuilt = true;
									for (Field innerfield : innerFields) {
										innerfield.setAccessible(true);
										Object innerObject = innerfield.get(object);
										BuilderAnotation innerJsonMapping = innerfield.getAnnotation(BuilderAnotation.class);
										if (innerJsonMapping != null) {
											try {
												if (innerfield.getType().isPrimitive() || innerfield.getType().isInstance(new String()) || String.class.isAssignableFrom(innerfield.getType()) || Number.class.isAssignableFrom(innerfield.getType())) {
													innerValuesMap.put(innerJsonMapping.name(), String.valueOf(innerObject));
													isInnerJsonBuilt = true;
												} /*else if (Date.class.isAssignableFrom(innerfield.getType())) {
													Date date = (Date) innerObject;
													DateUtils dateUtils = new DateUtils();
													innerValuesMap.put(innerJsonMapping.name(), dateUtils.formatDateToYYYYMMDDTHHMMSSSSSZ(date));
													isInnerJsonBuilt = true;
												}*/
											} catch (Exception e) {
												log.error("Error Occrued while Building InnerJson " + innerJsonMapping.name(), e);
											} finally {
												if (!isInnerJsonBuilt) {
													innerValuesMap.put(innerJsonMapping.name(), new Object());
												}
											}
										}
									}
									complexMap.put(jsonMapping.name(), innerValuesMap);
									isJsonBuilt = true;
								}
							}
						}
					} catch (Exception e) {
						if (e.getMessage().contains("lazily initialize")) {
							log.debug("Error Occrued (LazyInitializationException -failed to lazily initialize) while Building Json " + jsonMapping.name());
						} else {
							log.error("Error Occrued while Building Json " + jsonMapping.name(), e);
						}
					} finally {
						if (!isJsonBuilt) {
							complexMap.put(jsonMapping.name(), new Object());
						}
					}
				}
			}
			return complexMap;
		} catch (Exception e) {
			throw e;
		}
	}
}
