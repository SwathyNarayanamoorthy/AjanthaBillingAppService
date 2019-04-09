package com.ajantha.billing.parent.billingTO;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BuilderAnotation {
	String name();
}
