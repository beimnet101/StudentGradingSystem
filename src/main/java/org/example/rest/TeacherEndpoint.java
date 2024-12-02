package org.example.rest;
import io.swagger.annotations.Api;

import org.example.Dto.req.*;

import org.example.service.TeacherService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teacher")
@Api(value = "Library System")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherEndpoint {
    @EJB
    TeacherService TeacherService;

    @POST
    @Path("/PostStudentGrade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Response PostGrade(GiveGradeDto giveGradeDto){
        try{

            RegistrationResponseDto reponse = TeacherService.gradeYourSubject(giveGradeDto);
            return Response.ok(reponse).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) .entity("Failed to grade a student : " + e.getMessage()) .build();
        }
    }

    @POST
    @Path("/addSubject")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubject(SubjectRegisterDto subjectRegisterDto) {
        try {
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


