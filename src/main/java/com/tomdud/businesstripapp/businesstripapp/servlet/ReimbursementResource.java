package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reimbursement")
@WebServlet
public class ReimbursementResource {

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(reimbursementService.getAll()).build();
    }

    @GET
    @Path("/least")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeast() {
        return Response.ok().entity(reimbursementService.getLeast()).build();
    }


}
