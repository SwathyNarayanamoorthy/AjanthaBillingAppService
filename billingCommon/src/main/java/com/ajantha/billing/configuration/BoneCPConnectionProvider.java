/**
 *  Copyright 2010 Wallace Wadge
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.ajantha.billing.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import com.jolbox.bonecp.hooks.ConnectionHook;


/**
 * Hibernate Connection Provider.
 *
 * @author wallacew
 */
public class BoneCPConnectionProvider implements ConnectionProvider,Configurable,Stoppable {
	/**
	 * uid
	 */
	private static final long serialVersionUID = -5236029951415598543L;
	/** Config key. */
	protected static final String CONFIG_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_PASSWORD = "hibernate.connection.password";
	protected static final String REPORTING_DB_CONFIG_CONNECTION_PASS = "reporting.hibernate.connection.password";
	protected static final String CONFIG_IS_PASSWORD_ENCRYPTED = "is.password.encrypted";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_USERNAME = "hibernate.connection.username";
	protected static final String REPORTING_DB_CONFIG_CONNECTION_USERNAME = "reporting.hibernate.connection.username";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_URL = "hibernate.connection.url";
	protected static final String REPORTING_DB_CONFIG_CONNECTION_URL = "reporting.hibernate.connection.url";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_DRIVER_CLASS_ALTERNATE = "javax.persistence.jdbc.driver";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_PASSWORD_ALTERNATE = "javax.persistence.jdbc.password";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_USERNAME_ALTERNATE = "javax.persistence.jdbc.user";
	/** Config key. */
	protected static final String CONFIG_CONNECTION_URL_ALTERNATE = "javax.persistence.jdbc.url";
	
	protected static final String ENCRYPTION_KEY = "encryptionKey";
	/** Isolation level. */
	private Integer isolation;
	/** Autocommit option. */
	private boolean autocommit;
	/** Classloader to use to load the jdbc driver. */
	private ClassLoader classLoader;
	/** Configuration handle. */
	private BoneCPDataSource dataSource;
	/** Class logger. */
	private static Log logger = LogFactory.getLog(BoneCPConnectionProvider.class);

	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.hibernate.service.jdbc.connections.spi.ConnectionProvider#closeConnection(java.sql.Connection)
	 */
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	} 

	/**
	 * Pool configuration.
	 * @param props
	 * @throws HibernateException
	 */
	public void configure(Properties props) throws HibernateException {
		try {
			BoneCPConfig config = new BoneCPConfig(props);
			String url="";
			String username="";
			String password ="";
			
			url = props.getProperty(CONFIG_CONNECTION_URL);
			username = props.getProperty(CONFIG_CONNECTION_USERNAME);
			password = props.getProperty(CONFIG_CONNECTION_PASSWORD);

			String driver = props.getProperty(CONFIG_CONNECTION_DRIVER_CLASS);
			if (url != null) {
				config.setJdbcUrl(url);
			}
			if (username != null) {
				config.setUsername(username);
			}
			if (password != null) {
				config.setPassword(password);
			}

			// Remember Isolation level
			this.isolation = ConfigurationHelper.getInteger(AvailableSettings.ISOLATION, props);
			this.autocommit = ConfigurationHelper.getBoolean(AvailableSettings.AUTOCOMMIT, props);

			logger.debug(config.toString());

			if (driver != null && !"".equals(driver.trim())) {
				loadClass(driver);
			}
			if (config.getConnectionHookClassName() != null) {
				Object hookClass = loadClass(config.getConnectionHookClassName()).newInstance();
				config.setConnectionHook((ConnectionHook) hookClass);
			}
			// create the data source
			this.dataSource = new BoneCPDataSource(config);
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	/** Loads the given class, respecting the given classloader.
	 * @param clazz class to load
	 * @return Loaded class
	 * @throws ClassNotFoundException
	 * @throws Exception 
	 */
	protected Class<?> loadClass(String clazz) throws ClassNotFoundException, Exception {
		logger.info("Loaded Class : "+clazz);	
		if (this.classLoader == null) {
			return Class.forName(clazz);
		}
		return Class.forName(clazz, true, this.classLoader);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.hibernate.service.jdbc.connections.spi.ConnectionProvider#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		// set the Transaction Isolation if defined
		try {
			// set the Transaction Isolation if defined
			if ((this.isolation != null) && (connection.getTransactionIsolation() != this.isolation.intValue())) {
				connection.setTransactionIsolation (this.isolation.intValue());
			}

			// toggle autoCommit to false if set
			if ( connection.getAutoCommit() != this.autocommit ){
				connection.setAutoCommit(this.autocommit);
			}

			return connection;
		} catch (SQLException e){
			try {
				connection.close();
			} catch (Exception e2) {
				logger.warn("Setting connection properties failed and closing this connection failed again", e2);
			}
			
			throw e;
		} 
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.hibernate.service.jdbc.connections.spi.ConnectionProvider#supportsAggressiveRelease()
	 */
	public boolean supportsAggressiveRelease() {
		return false;
	}

	/** Returns the classloader to use when attempting to load the jdbc driver (if a value is given).
	 * @return the classLoader currently set.
	 */
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}

	/** Specifies the classloader to use when attempting to load the jdbc driver (if a value is given). Set to null to use the default
	 * loader.
	 * @param classLoader the classLoader to set
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals( unwrapType ) ||
			BoneCPConnectionProvider.class.isAssignableFrom( unwrapType );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		if ( ConnectionProvider.class.equals( unwrapType ) ||
				BoneCPConnectionProvider.class.isAssignableFrom( unwrapType ) ) {
			return (T) this;
		}
			
		throw new UnknownUnwrapTypeException( unwrapType );
	}

	
	/**
	 * Legacy conversion.
	 * @param map
	 * @return Properties
	 */
	private Properties mapToProperties(Map<String, String> map) {
		   Properties p = new Properties();
		   for (Map.Entry<String,String> entry : map.entrySet()) {
		     p.put(entry.getKey(), entry.getValue());
		   }
		   return p;
		 }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void configure(Map configurationValues) {
			configure(mapToProperties(configurationValues));
	}

	@Override
	public void stop() {
		close();
	}

	/**
	 * alias for stop.
	 */
	public void close(){
		this.dataSource.close();
	}
	
	public DataSource getDataSource() {
		return this.dataSource;
	}
}
