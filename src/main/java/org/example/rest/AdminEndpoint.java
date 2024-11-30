package org.example.rest;

import io.swagger.annotations.Api;
import org.example.Dto.*;
import org.example.model.Users;
import org.example.service.AdminService;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
@Api(value = "Library System")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminEndpoint {
    @EJB
      AdminService adminService;
    @GET
    @Path("/Allusers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@QueryParam("start") @DefaultValue("0") int start,
                                @QueryParam("max") @DefaultValue("10") int max) {
        try {
            // Call the AdminService to fetch user DTOs
            List<userDto> users = adminService.Users(start, max);

            // Return the response with the list of users
            return Response.ok(users).build();
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch users: " + e.getMessage())
                    .build();
        }
    }



}

