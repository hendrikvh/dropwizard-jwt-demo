package com.hendrikvh.demos.jwtdemo.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import com.hendrikvh.demos.jwtdemo.auth.Secrets;
import com.hendrikvh.demos.jwtdemo.auth.jwt.UserRoles;
import com.hendrikvh.demos.jwtdemo.resources.dto.LoginResponse;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jersey.caching.CacheControl;

/**
 * Exchanges login information for a JWT token.
 *
 * @author Hendrik van Huyssteen
 * @since 09 Aug 2017
 */

@Path("auth")
@Produces(APPLICATION_JSON)
public class LoginResource {

	@GET
	@Path("/login")
	@CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
	public final LoginResponse doLogin(@Auth PrincipalImpl user) throws JoseException {
		return new LoginResponse(buildToken(user).getCompactSerialization());
	}

	/**
	 * Example token builder. This would be handled by some role mapping system in production.
	 *
	 * @return Example token with the {@link UserRoles#ROLE_ONE} role.
	 */
	private JsonWebSignature buildToken(PrincipalImpl user) {
		// These claims would be tightened up for production
		final JwtClaims claims = new JwtClaims();
		claims.setSubject("1");
		claims.setStringClaim("roles", UserRoles.ROLE_ONE);
		claims.setStringClaim("user", user.getName());
		claims.setIssuedAtToNow();
		claims.setGeneratedJwtId();

		final JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(HMAC_SHA256);
		jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));
		return jws;
	}
}