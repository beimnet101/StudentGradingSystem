package org.example.rest;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@SwaggerDefinition(info = @Info(
        title = "Student Grading System",
        description = "Student Grading System",
        version = "1.0.0",
        contact = @Contact(
                name = "ATLAS COMPUTER TECHNOLOGY PLC",
                email = "info@act.com.et",
                url = "https://act.com.et/"
        )
)
)
public class RestApplication extends Application {
}