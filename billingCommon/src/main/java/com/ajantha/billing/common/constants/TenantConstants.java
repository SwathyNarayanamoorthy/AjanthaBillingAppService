package com.ajantha.billing.common.constants;

public class TenantConstants
{
	// Tenant DataCache constants
	public static final String TENANT_DATACACHE_PROPERTIES_FILE = "tenant.datacache.properties.file";
	
	// Tenant loader constants
	public static final String TENANT_LOADER = "tenant.loader.class";
	public static final String TENANT_LOADER_PROPERTIES_FILE = "tenant.loader.properties.file";
	public static final String TENANT_LOADER_JNDI = "tenant.loader.jndi";
	
	public static final String TENANT_DB_LOADER = "tenant.db.loader";
	public static final String ES_HOST = "tenant.es.host";
	public static final String ES_PORT = "tenant.es.port";
	public static final String IS_ES_DYNAMO_LOCAL = "tenant.isESDynamoLocal";
	
	public static final String DYNAMO_DB = "dynamoDB";
	public static final String DYNAMO_DB_REGION = "dynamoDbRegion";
	
	
	public static final String TENANT_LOADER_DRIVER = "tenantportal.database.driver";
	public static final String TENANT_LOADER_DB_URL = "tenantportal.database.dburl";
	public static final String TENANT_LOADER_USER_NAME = "tenantportal.database.user.name";
	public static final String TENANT_LOADER_PASS = "tenantportal.database.password";
	public static final String TENANT_VERSION = "tenantportal.version";
	public static final String TP_DROPLET_NAME = "tenantportal.droplet.name";

	// Tenant identifier constants
	public static final String TENANT_IDENTIFIER = "tenant.identifier.class";
	public static final String TENANT_IDENTIFIER_ATTRIBUTE = "tenant.identifier.attribute";
	public static final String TENANT_IDENTIFIER_QUERY_PARAM = "tenant.identifier.query.param";
	public static final String TENANT_IDENTIFIER_HTTP_HEADER = "tenant.identifier.http.header";
	
	public static final String TENANT_DEPLOYMENT_CLOUD ="tenant.iscloud";

	// Tenant registration listener

	

	// Other constants
	public static final String TENANT_ID_SEPARATOR = "$.";
	public static final String TENANT_MODULES_SEPARATOR = ",";
	public static final String TENANT_METADATA = "TENANT_METADATA_";
	public static final String TENANT_SEPERATOR = "_";
	public static final String TENANT_ID = "tenantId";
	public static final String TENANT_NAME = "tenantName";
	public static final String TENANT_MODULES = "tenantModules";
	public static final String TENANT_LOG_LEVEL = "log.level";
	public static final String TENANT_PROPERTY_JNDI_NAME = "jndiName";
	public static final String TENANT_EM_ENABLE = "em.enable";
	public static final String TENANT_WEBHOOK_ENABLE = "is.webhook.enable";
	public static final String FILE_PATH_SEPERATOR = "/";
	public static final String REGEX_PATTEN_TO_REPLACE_STRING_TO_FILE_PATH_SEPERATOR = "[/\\\\]+";
	public static final String ENCRYPTION_ENABLED = "encryptionEnabled";
	public static final String ALGORITHM_NAME = "AES";
	public static final String TENANT_BUCKET_NAME_SEPERATOR = "-";
	public static final String TENANT_REGISTRATION_LISTENER_PATH = "/servlet/TenantRegistration";
	public static final String TENANT_ATTRIBUTE = "tin";
	public static final String TENANT_EXPIRATION_MAP = "TENANT_EXPIRATION_MAP";
	public static final String TENANT_LOADER_PASSWORD = "tenantportal.database.password";
		
	public static final String TENANT_PROPERTIES = "tenant.properties";
	public static final String IS_IP_ADDRESS = "isIPAddress";
	
	
	
	public static final String REDIS_PROPERTIES = "redis.properties";

	public static final String TYPE = "type";
	public static final String ENTITY_NAME = "entityName";
	
	public static final String USER_PREFERENCE_META_DATA = "USER_PREFERENCE_META_DATA";
	
	
	public static final String TENANT_USER_ACCESS_KEY = "tenant.iam.access.key";
	public static final String TENANT_USER_SECRET_KEY = "tenant.iam.secret.key";
	public static final String TENANT_USER_ACCESS_KEY_STATUS = "tenant.iam.access.key.status";
	
	public static final String DOMAIN_NAME = "droplet.domain.name";
	public static final String IPV_RESOURCE_TYPE = "ipv.type";
	public static final String HOSTED_ZONE_ID = "droplet.hostedZoneId";
	
	
	public static final String TENANT_DROPLET_NAME = "tenant.droplet.name";

	public static final String TENANT_INITIALIZATION_LOCK = "TENANT_INITIALIZATION_LOCK_";
	
	public static final String REPUBLISH_SYSTEM_PROPERTY_LOCK = "REPUBLISH_SYSTEM_PROPERTY_LOCK";
	
	public static final String UPDATE_TENANT_METADATA_LOCK = "UPDATE_TENANT_METADATA_LOCK_";
	
	public static final String INITIALIZE_TURN_ON_MODULES = "INITIALIZE_TURN_ON_MODULES_";
	
	public static final String TOMCAT_SSL_CLUSTER_IPS = "tomcat.ssl.cluster.ips";
	
	public static final String FILE_NAME = "fileName";
	
	public static final class RedisKeyConstants {
		public static final String PRR_PORTAL_USER_ACCESS = "PRR_PORTAL_USER_ACCESS";
		
		public static final String PRR_SYSTEM_PROPERTIES_KEY = "_PRR_System_Properties";	
		
		public static final String PRR_SIGN_IN_ATTEMPTS = "sign_in_attempts";
		
		public static final String FAILED_REQUEST_THREAD = "FailedRequestThreads";
	}
	public static final class LambdaConstants{
		public static final String LAMBDA_FUNCTION_NAME = "_ProcessRequest";
		
		public static final String LAMBDA_REGION = "lambdaRegion";
		
		public static final String NEW_IMAGE = "NewImage";
		
		public static final String INDEX_FAILURE = "indexFailure";
	}
	public static final String TENANT_API_KEYS = "TENANT_API_KEYS";

	public static final String TENANT_API_PUBLIC_KEY = "api.public.key";
	
	public static final String TENANT_API_PRIVATE_KEY = "api.private.key";
	
	public static final String TENANT_SQS_REGEION = "sqsRegion";
}