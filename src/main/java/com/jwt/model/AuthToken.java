package com.jwt.model;

import java.util.List;

public class AuthToken {

	private String token;
	private String username;
	private String roleName;
	private List<String> permissions;

	public AuthToken() {

	}

	public AuthToken(String token, String username, String roleName, List<String> permissions) {
		this.token = token;
		this.username = username;
		this.roleName = roleName;
		this.permissions = permissions;
	}

	public AuthToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}
