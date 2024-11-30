package org.example.rest;

import io.swagger.annotations.Api;
import org.example.Dto.*;
import org.example.Dto.req.RegistrationResponseDto;
import org.example.Dto.req.UserReq;
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
    @POST
    @Path("/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserReq userReq) {
        try {
            // Call the AdminService to add the user
            RegistrationResponseDto response = adminService.addUser(userReq);

            // Return a success response with the result
            return Response.ok(response).build();
        } catch (Exception e) {
            // Log the exception (replace with proper logging in production)
            e.printStackTrace();

            // Return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch users: " + e.getMessage())
                    .build();
        }
    }

    @Path("/getuser")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUser(@QueryParam("id") int userId) {
        // Call the service to fetch the user by ID
        userDto user = adminService.getUserById(userId);

        // If the user is not found, return a NOT_FOUND response
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with ID: " + userId)
                    .build();
        }

        // Otherwise, return the user data
        return Response.ok(user).build();
    }






}

