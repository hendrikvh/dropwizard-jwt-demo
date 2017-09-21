package com.hendrikvh.demos.jwtdemo.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.auth.Authorizer;

/**
 * Determines if a user is authorised to access an API endpoint, after they were authenticated with {@link JwtAuthenticator}.
 *
 * See {@link com.hendrikvh.demos.jwtdemo.resources.ProtectedResourceOne}.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep 2017
 */
public class JwtAuthoriser implements Authorizer<ExampleUser> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthoriser.class);

	@Override
	public boolean authorize(ExampleUser exampleUser, String requiredRole) {
		if (exampleUser == null) {
			LOGGER.warn("msg=user object was null");
			return false;
		}

		String roles = exampleUser.getRoles();
		if (roles == null) {
			LOGGER.warn("msg=roles were null, user={}, userId={}", exampleUser.getName(), exampleUser.getId());
			return false;
		}
		return roles.contains(requiredRole);
	}
}