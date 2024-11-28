package org.example.rest;

import io.swagger.annotations.Api;
import org.example.Dto.req.LoanReq;
import org.example.Dto.res.BookResDto;
import org.example.Dto.res.StatusDto;
import org.example.service.LibraryUserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Api(value = "Library System")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LibraryUserEndPoint {
 @Inject
 LibraryUserService userlibraryService;
    @GET
    @Path("/listAllBooks")
    public List<BookResDto> listAllBook(@QueryParam("start")Integer start, @QueryParam("end") Integer end){
        return userlibraryService.listAllBook(start,end);
    }
    @POST
    @Path("/createCatagory")
    public StatusDto create(String catagory){
        return (StatusDto) userlibraryService.findByCatagory(catagory);
    }
    @POST
    @Path("/orderBook")
    public StatusDto create(LoanReq loanReq, String title){
        return this.userlibraryService.orderBook(loanReq,title);
    }
}
