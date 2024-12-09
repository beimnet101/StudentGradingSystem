package org.example.rest;

import io.swagger.annotations.*;
import org.example.Dto.*;
import org.example.Dto.req.AssignNewTeacherDto;
import org.example.Dto.req.RegistrationResponseDto;
import org.example.Dto.req.SubjectRegisterDto;
import org.example.Dto.req.UserReq;

import org.example.Dto.res.AuthorizationResponse;
import org.example.security.keycloack.Authenticate;
import org.example.security.keycloack.AuthenticationService;
import org.example.service.AdminService;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

@Path("/admin")
@Api(value = "admin services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminEndpoint {

    @EJB
    AdminService adminService;
    @Inject
    Authenticate authenticate;
    @Inject
    AuthenticationService authenticationService;
    AuthorizationResponse authorizationResponse=new AuthorizationResponse();


    @GET
    @Path("/Allusers")
    @ApiOperation(value = "Get All Users",
            notes = "Fetches all users from the system, with pagination support.",
            response = userDto.class)
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

    @GET
    @Path("/AllRegisteredSubjects")
    @ApiOperation(value = "Get All Registered Subjects",
            notes = "Fetches all registered subjects from the system, with pagination support.",
            response = SubjectDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRegistered(@QueryParam("start") @DefaultValue("0") int start,
                                     @QueryParam("max") @DefaultValue("10") int max) {
        try {
            // Call the AdminService to fetch subject DTOs
            List<SubjectDto> subjects = adminService.Subjects(start, max);

            // Return the response with the list of subjects
            return Response.ok(subjects).build();
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch subjects: " + e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/assignTeacherForaSubject")
    @ApiOperation(value = "Assign a  Teacher to a Subject",
            notes = "Assigns a teacher to a subject based on the subject code and teacher ID.",
            response = RegistrationResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignTeacher(@HeaderParam("authorization") String authorizationHeader,AssignNewTeacherDto assignNewTeacherDto) {



        try {
            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization header is missing or invalid").build();
            }

            String token = authorizationHeader;

            // Check authentication
            boolean authenticated = authenticationService.authenticateService(token, "admin");
            if (!authenticated) {
                 authorizationResponse.setMsg("not authorized");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(authorizationResponse).build();
            }



            RegistrationResponseDto reponse = adminService.assignSubjectTeacher(assignNewTeacherDto);
            return Response.ok(reponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to assign new teacher: " + e.getMessage()).build();
        }
    }


    @PUT
    @Path("/assignNewTeacherForaSubject")
    @ApiOperation(value = "Assign a New Teacher to a Subject",
            notes = "Assigns a new teacher to a subject based on the subject code and teacher ID.",
            response = RegistrationResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignNewTeacher(@HeaderParam("authorization") String authorizationHeader,AssignNewTeacherDto assignNewTeacherDto) {
        try {

            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization header is missing or invalid").build();
            }

            String token = authorizationHeader;

            // Check authentication
            boolean authenticated = authenticationService.authenticateService(token, "admin");
            if (!authenticated) {
                authorizationResponse.setMsg("not authorized");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity( authorizationResponse).build();
            }

            String subjectCode = assignNewTeacherDto.getSubjectCode();
            int newTeacherId = assignNewTeacherDto.getTeacherId();
            RegistrationResponseDto response = adminService.updateSubjectTeacher(subjectCode, newTeacherId);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to assign new teacher: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/byRole")
    @ApiOperation(value = "Get Users by Role",
            notes = "Fetches users based on the specified role ID, with pagination support.",
            response = userDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByRole(
                                   @QueryParam("roleId") int roleId,
                                   @QueryParam("start") @DefaultValue("0") int start,
                                   @QueryParam("max") @DefaultValue("10") int max) {
        try {

            // Call the AdminService to fetch user DTOs by role
            List<userDto> users = adminService.UsersRole(roleId, start, max);

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
    @ApiResponses({
            @ApiResponse(code = 200, message = "User created successfully", response = RegistrationResponseDto.class),
            @ApiResponse(code = 401, message = "Unauthorized: Invalid or missing token"),
            @ApiResponse(code = 403, message = "Forbidden: You do not have the required permissions")
    })
    @ApiOperation(value = "Add a New User",
            notes = "Adds a new user to the system with the provided user details. You must pass a valid Bearer token in the Authorization header.",
            response = RegistrationResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("authorization") String authorizationHeader, UserReq userReq) {
        try {
            // Check if the authorization header is missing or empty
            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                authorizationResponse.setMsg("not authorized");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity( authorizationResponse).build();
            }

            String token = authorizationHeader;

            // Check authentication
            boolean authenticated = authenticationService.authenticateService(token, "admin");
            System.out.println("Authenticated: " + authenticated);

            // Check if authentication failed and return FORBIDDEN (403)
            if (!authenticated) {
                System.out.println("Authentication failed, returning FORBIDDEN response");
                return Response.status(Response.Status.FORBIDDEN)  // Corrected to FORBIDDEN
                        .entity("Forbidden: You do not have the required permissions.")
                        .build();
            } else {
                // Authentication successful, proceed to add user
                System.out.println("Authentication successful, proceeding to add user");
                RegistrationResponseDto response = adminService.addUser(userReq);

                // Return the success response with the result
                return Response.ok(response).build();
            }

        } catch (Exception e) {
            // Return an error response with the exception message
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to add user: " + e.getMessage())
                    .build();
        }
    }
    @POST
    @Path("/addSubject")
    @ApiOperation(value = "Add a New Subject",
            notes = "Adds a new subject to the system with the provided subject details.",
            response = RegistrationResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubject(@HeaderParam("authorization") String authorizationHeader,SubjectRegisterDto subjectRegisterDto) {
        try {
            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization header is missing or invalid").build();
            }

            String token = authorizationHeader;

            // Check authentication
            boolean authenticated = authenticationService.authenticateService(token, "admin");
            if (!authenticated) {
                authorizationResponse.setMsg("not authorized");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity( authorizationResponse).build();
            }
            // Call the AdminService to add the subject
            RegistrationResponseDto response = adminService.addSubject(subjectRegisterDto);

            // Return a success response with the result
            return Response.ok(response).build();
        } catch (Exception e) {
            // Log the exception (replace with proper logging in production)
            e.printStackTrace();

            // Return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch subjects: " + e.getMessage())
                    .build();
        }
    }
}