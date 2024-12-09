package org.example.rest;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SwaggerDefinition.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@SwaggerDefinition(
        info = @Info(
                title = "Student Grading System",
                description = "Student Grading System API Documentation",
                version = "1.0.0",
                contact = @Contact(
                        name = "ATLAS COMPUTER TECHNOLOGY PLC",
                        email = "info@act.com.et",
                        url = "https://act.com.et/"
                )
        ),
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefinitions = {
                        @ApiKeyAuthDefinition(
                                name = "Authorization",  // The header field name
                                key = "Bearer Token",
                                in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER // Indicates the token will be sent in the header
                                 
                        )
                }
        )
)
public class RestApplication extends Application {
}
