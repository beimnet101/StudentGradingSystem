package org.example.rest;

import io.swagger.annotations.*;
import org.example.Dto.LoginResponseDto;
import org.example.Dto.req.UserLoginRequest;
import org.example.security.keycloack.Authenticate;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Api(value = "Login")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @EJB
    Authenticate authenticate;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Authenticate user and return access token", notes = "This endpoint accepts the username and password, " +
            "authenticates the user via Keycloak, and returns an access token if valid.",
    response=LoginResponseDto.class)




    public Response login(UserLoginRequest loginRequest) {
        try {
            // Extract credentials from the login request
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            // Authenticate with Keycloak and get the access token
            String token = authenticate.getAccessToken(username, password);

            if (token != null) {
                // Return the access token in the response
                return Response.ok(new LoginResponseDto(token)).build();
            } else {
                // Unauthorized if authentication fails
                LoginResponseDto loginResponseDto=new LoginResponseDto();
                loginResponseDto.setMsg("incorrect password or username");

                return Response.status(Response.Status.UNAUTHORIZED).
                        entity(loginResponseDto).build();

            }

        } catch (Exception e) {
            // Handle internal server errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the login: " + e.getMessage()).build();
        }
    }
}
