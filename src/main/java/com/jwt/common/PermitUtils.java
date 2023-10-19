package com.jwt.common;

import java.io.IOException;

import io.permit.sdk.Permit;
import io.permit.sdk.PermitConfig;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.api.PermitContextError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.openapi.models.RoleRead;
import io.permit.sdk.openapi.models.UserCreate;
import io.permit.sdk.openapi.models.UserRead;

public class PermitUtils {

	 Permit permit;
	 UserRead user;
	 
	public void PermitUtils(String userKey) throws PermitContextError, PermitApiError {
		this.permit = new Permit(new PermitConfig.Builder(
				"permit_key_GeDTSDhhpFendZGnArOBngcOzk6HJJMXug1HAoYUekM9uDRaQZYQvuGmjcU59bIMYcpAdev9Yme9EHx0CHXDsc")
				.withPdpAddress("https://cloudpdp.api.permit.io")
		        .withDebugMode(true).build());
		try {
			this.user = permit.api.users.sync(
					new UserCreate(userKey)
					.withEmail(userKey)
					).getResult();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	public boolean permitCheck(User user, String action, Resource resource) throws IOException {
		boolean check = permit.check(user, action, resource);
		if (check) {
			return true;
		}
		return false;
	}
	
	public RoleRead userRole(String roleKey) throws PermitContextError, PermitApiError, IOException {
		this.permit = new Permit(new PermitConfig.Builder(
				"permit_key_GeDTSDhhpFendZGnArOBngcOzk6HJJMXug1HAoYUekM9uDRaQZYQvuGmjcU59bIMYcpAdev9Yme9EHx0CHXDsc")
				.withPdpAddress("https://cloudpdp.api.permit.io")
		        .build());
		RoleRead roleRad=permit.api.roles.get(roleKey);
		return roleRad;
	}
}
