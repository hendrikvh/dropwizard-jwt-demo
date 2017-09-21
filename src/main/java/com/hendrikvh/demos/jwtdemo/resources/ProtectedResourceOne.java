package com.hendrikvh.demos.jwtdemo.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hendrikvh.demos.jwtdemo.auth.jwt.ExampleUser;
import com.hendrikvh.demos.jwtdemo.auth.jwt.UserRoles;
import com.hendrikvh.demos.jwtdemo.resources.dto.ProtectedResourceResponse;

import io.dropwizard.auth.Auth;

@Path("/protectedResourceOne")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProtectedResourceOne {

	@GET
	@RolesAllowed({ UserRoles.ROLE_ONE })
	public ProtectedResourceResponse getAuthProcessingTime(@Auth ExampleUser user) throws Exception {

			return new ProtectedResourceResponse(user.getRoles(), user.getName());

	}
}