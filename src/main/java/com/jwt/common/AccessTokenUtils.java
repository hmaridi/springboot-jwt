package com.jwt.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AccessTokenUtils {

	public static String getUserIdFromToken() throws Exception {
		String email = jwtTokenDecode(getToken());
		return email;
	}

	private static String getToken() {
		return getAuthorizationHeader().split(" ")[1];
	}

	private static String getAuthorizationHeader() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request.getHeader("authorization");
	}

	public static String jwtTokenDecode(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("email").asString();
		} catch (Exception e) {
			throw new JWTDecodeException("JWT Decode verification is fails");
		}
	}

	public static String getUserNameFromToken() throws Exception {
		String email = jwtTokenDecode(getToken());
		return email;
	}




}
