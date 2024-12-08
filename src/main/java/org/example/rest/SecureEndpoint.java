package org.example.rest;

import io.swagger.annotations.Api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/secure")
@Api(value = "Security services")
public class SecureEndpoint {

    @GET
     // Replace "user" with a role defined in Keycloak
    public Response data() {
        return Response.ok("This is a secure endpoint!").build();
    }
}

