package org.example.rest;
import io.swagger.annotations.Api;
import org.example.Dto.res.GradeReportDto;
import org.example.Dto.userDto;
import org.example.model.Grade;
import org.example.service.GpaService;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;


@Path("/student")
@Api(value = "Library System")
public class StudentEndpoint {
@EJB
GpaService gpaService;
    @GET
    @Path("/viewyourgpa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentGpa(@QueryParam("studentId") int studentId) {
        try {
            // Validate the input
            if (studentId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid student ID provided.")
                        .build();
            }

            // Calculate GPA
            double gpa = gpaService.calculateGPA(studentId);

            // Return GPA as JSON response
            return Response.ok(gpa).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to calculate GPA: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/viewgrades")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentGrades(@QueryParam("studentId") int studentId) {
        try {
            // Fetch the student's grades from the database
            List<GradeReportDto> grades = gpaService.getGrades(studentId);

            // Check if grades were found
            if (grades.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No grades found for student ID: " + studentId)
                        .build();
            }

            // Return the response with the grades
            return Response.ok(grades).build();
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch grades: " + e.getMessage())
                    .build();
        }
    }




}