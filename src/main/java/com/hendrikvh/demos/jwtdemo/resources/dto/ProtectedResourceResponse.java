package com.hendrikvh.demos.jwtdemo.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reponse returned to user for demo purposes, indicating username and role that was present in JWT.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep 2017
 */
public class ProtectedResourceResponse {

	public ProtectedResourceResponse(String role, String username) {
		this.role = role;
		this.username = username;
	}

	@JsonProperty("role")
	public String role;

	@JsonProperty("username")
	public String username;
}