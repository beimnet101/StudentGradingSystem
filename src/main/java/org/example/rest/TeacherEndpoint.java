package org.example.rest;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.Dto.req.*;

import org.example.Dto.userDto;
import org.example.security.keycloack.AuthenticationService;
import org.example.service.TeacherService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teacher")
@Api(value = "teacher services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherEndpoint {
    @EJB
    TeacherService TeacherService;
    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/PostStudentGrade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer <your-token>", required = true, dataType = "string", paramType = "header")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Response PostGrade(@HeaderParam("authorization") String authorizationHeader,GiveGradeDto giveGradeDto){
        try{
            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization header is missing or invalid").build();
            }

            String token = authorizationHeader;

            // Check authentication
            boolean authenticated = authenticationService.authenticateService(token, "teacher");
            if (!authenticated) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Forbidden: You do not have the required permissions.").build();
            }





            RegistrationResponseDto reponse = TeacherService.gradeYourSubject(giveGradeDto);
            return Response.ok(reponse).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) .entity("Failed to grade a student : " + e.getMessage()) .build();
        }
    }

    @POST
    @Path("/addSubject")
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
            boolean authenticated = authenticationService.authenticateService(token, "teacher");
            if (!authenticated) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Forbidden: You do not have the required permissions.").build();
            }

            // Call the AdminService to add the user
            RegistrationResponseDto response = TeacherService.addSubject(subjectRegisterDto);

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












}


