package com.ajantha.billing.user.info.to;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ajantha.billing.parent.billingTO.BuilderAnotation;

/**
 * 
 * @author sivabharani
 *
 */
@Entity
@Table(name = "user_info")
public class UserInfoTO implements java.io.Serializable
{

	private static final long serialVersionUID = -3806406130942916317L;

	@BuilderAnotation(name = "USER_ID")
	private long userId;

	@BuilderAnotation(name = "USER_NAME")
	private String userName;

	private String password;

	@BuilderAnotation(name = "PHONE_NUMBER")
	private String phoneNumber;

	public UserInfoTO() {
		//Suppress
	}

	public UserInfoTO(long userId) {
		this.userId = userId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Column(name = "user_name", nullable = false, length = 256)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", length = 250)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone_number",length = 256)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}