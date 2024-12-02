package org.example.rest;

import io.swagger.annotations.Api;
import org.example.Dto.*;
import org.example.Dto.req.AssignNewTeacherDto;
import org.example.Dto.req.RegistrationResponseDto;
import org.example.Dto.req.SubjectRegisterDto;
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




    @GET
    @Path("/AllRegisteredSubjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRegistered(@QueryParam("start") @DefaultValue("0") int start,
                                     @QueryParam("max") @DefaultValue("10") int max) {
        try {
            // Call the AdminService to fetch user DTOs
          List<SubjectDto> subjects= adminService.Subjects(start, max);

            // Return the response with the list of users
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Response assignTeacher(AssignNewTeacherDto assignNewTeacherDto){
        try{

            RegistrationResponseDto reponse =adminService.assignSubjectTeacher(assignNewTeacherDto);
            return Response.ok(reponse).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) .entity("Failed to assign new teacher: " + e.getMessage()) .build();
        }
    }




    @PUT
    @Path("/assignNewTeacherForaSubject")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Response assignNewTeacher(AssignNewTeacherDto assignNewTeacherDto){
        try{
            String subjectCode=assignNewTeacherDto.getSubjectCode();
            int newTeacherId=assignNewTeacherDto.getTeacherId();
            RegistrationResponseDto reponse =adminService.updateSubjectTeacher(subjectCode,newTeacherId);
            return Response.ok(reponse).build();
               } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) .entity("Failed to assign new teacher: " + e.getMessage()) .build();
        }
    }
    @GET
        @Path("/byRole")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getUsersByRole(@QueryParam("roleId") int roleId,
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


    @POST
    @Path("/addSubject")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubject(SubjectRegisterDto subjectRegisterDto) {
        try {
            // Call the AdminService to add the user
            RegistrationResponseDto response = adminService.addSubject(subjectRegisterDto);

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

