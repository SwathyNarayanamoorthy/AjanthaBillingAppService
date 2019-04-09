package com.ajantha.billing.configuration;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * Dialect mapping was not found for certain JDBC type in Hibernate 4. So this is registered below
 * 
 * @author sivabharani
 * @since Billing 1.0
 */
public class BillingCustomSQLServerDialect extends SQLServerDialect
{
	public BillingCustomSQLServerDialect() {
		super();
		registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
		registerHibernateType(Types.LONGVARCHAR, StandardBasicTypes.TEXT.getName());
	}

}
