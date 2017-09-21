package com.hendrikvh.demos.jwtdemo.core;

import java.io.UnsupportedEncodingException;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtContext;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.hendrikvh.demos.jwtdemo.auth.jwt.ExampleUser;
import com.hendrikvh.demos.jwtdemo.auth.AuthFilterUtils;
import com.hendrikvh.demos.jwtdemo.health.JwtDemoHealthCheck;
import com.hendrikvh.demos.jwtdemo.resources.LoginResource;
import com.hendrikvh.demos.jwtdemo.resources.ProtectedResourceOne;
import com.hendrikvh.demos.jwtdemo.resources.ProtectedResourceTwo;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Main app demonstrating JWT and basic auth.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep 2017
 */
public class MainApp extends Application<DemoConfiguration> {

	public static void main(final String[] args) throws Exception {
		new MainApp().run(args);
	}

	@Override
	public String getName() {
		return "Dropwizard JWT Demo";
	}

	@Override
	public void initialize(final Bootstrap<DemoConfiguration> bootstrap) {
		// Nothing required for now
	}

	@Override
	public void run(final DemoConfiguration configuration, final Environment environment) throws UnsupportedEncodingException {
		registerResources(environment);

		registerAuthFilters(environment);

	}

	/**
	 * Registers the resources that will be exposed to the user.
	 */
	private void registerResources(Environment environment) {
		environment.healthChecks().register("running", new JwtDemoHealthCheck());
		environment.jersey().register(new LoginResource());
		environment.jersey().register(new ProtectedResourceOne());
		environment.jersey().register(new ProtectedResourceTwo());
	}

	/**
	 * Registers the filters that will handle authentication
	 */
	private void registerAuthFilters(Environment environment) {
		AuthFilterUtils authFilterUtils = new AuthFilterUtils();
		final AuthFilter<BasicCredentials, PrincipalImpl> basicFilter = authFilterUtils.buildBasicAuthFilter();
		final AuthFilter<JwtContext, ExampleUser> jwtFilter = authFilterUtils.buildJwtAuthFilter();

		final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
				ImmutableMap.of(PrincipalImpl.class, basicFilter, ExampleUser.class, jwtFilter));
		final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
				ImmutableSet.of(PrincipalImpl.class, ExampleUser.class));

		environment.jersey().register(feature);
		environment.jersey().register(binder);
		environment.jersey().register(RolesAllowedDynamicFeature.class);
	}
}