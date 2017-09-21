package com.hendrikvh.demos.jwtdemo.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Check if the app is up and running.
 *
 * @author Hendrik van Huyssteen
 * @since 21 Sep Aug 2017
 */
public class JwtDemoHealthCheck extends HealthCheck {


	@Override
	protected Result check() throws Exception {
			return Result.healthy();
	}
}