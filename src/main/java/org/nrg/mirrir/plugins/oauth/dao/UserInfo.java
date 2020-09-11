package org.nrg.mirrir.plugins.oauth.dao;

import lombok.Builder;

@Builder
public class UserInfo {
	private String name;
	
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
