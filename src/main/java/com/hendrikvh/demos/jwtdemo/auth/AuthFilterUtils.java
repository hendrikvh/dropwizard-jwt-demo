package com.hendrikvh.demos.jwtdemo.auth;

import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.hendrikvh.demos.jwtdemo.auth.basic.BasicAuthenticator;
import com.hendrikvh.demos.jwtdemo.auth.jwt.ExampleUser;
import com.hendrikvh.demos.jwtdemo.auth.jwt.JwtAuthenticator;
import com.hendrikvh.demos.jwtdemo.auth.jwt.JwtAuthoriser;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;

/**
 * Auth filter utilities used elsewhere, just to keep all the auth config nice and central.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep 2017
 */
public class AuthFilterUtils {

	public BasicCredentialAuthFilter<PrincipalImpl> buildBasicAuthFilter() {
		return new BasicCredentialAuthFilter.Builder<PrincipalImpl>().setAuthenticator(new BasicAuthenticator()).setPrefix("Basic")
				.buildAuthFilter();
	}

	public AuthFilter<JwtContext, ExampleUser> buildJwtAuthFilter() {
		// These requirements would be tightened up for production use
		final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
				.setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

		return new JwtAuthFilter.Builder<ExampleUser>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
				.setAuthenticator(new JwtAuthenticator()).setAuthorizer(new JwtAuthoriser()).buildAuthFilter();
	}
}
