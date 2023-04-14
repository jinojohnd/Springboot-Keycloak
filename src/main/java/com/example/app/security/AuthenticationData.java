package com.example.app.security;

import java.io.Serializable;

public class AuthenticationData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String realm;

	public AuthenticationData(String realm) {
		super();
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
}
