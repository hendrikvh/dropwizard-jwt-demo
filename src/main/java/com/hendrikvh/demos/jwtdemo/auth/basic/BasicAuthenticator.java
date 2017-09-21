package com.hendrikvh.demos.jwtdemo.auth.basic;

import java.util.Optional;

import com.hendrikvh.demos.jwtdemo.auth.Secrets;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Validates credentials for basic auth on login in {@link com.hendrikvh.demos.jwtdemo.resources.LoginResource}.
 *
 * @author Hendrik van Huyssteem
 * @since 21 Sep 2017
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {

	@Override
	public Optional<PrincipalImpl> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (isValidCredentials(credentials)) {
			return Optional.of(new PrincipalImpl(credentials.getUsername()));
		}
		return Optional.empty();
	}

	private boolean isValidCredentials(BasicCredentials credentials) {
		return Secrets.LOGIN_USERNAME.equals(credentials.getUsername()) && (Secrets.LOGIN_PASSWORD.equals(credentials.getPassword()));
	}
}