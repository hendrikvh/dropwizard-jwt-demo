package com.hendrikvh.demos.jwtdemo.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Communicates JWT token to be used after login.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep 2017
 */
public class LoginResponse {

	public LoginResponse(String token) {
		this.token = token;
	}

	@JsonProperty("token")
	public String token;

}