package at.htl.service;

import at.htl.repositories.OeffnungszeitenRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/menue/oeffnungszeiten")
public class OeffnungszeitService {
    @Inject
    private OeffnungszeitenRepository oeffnungszeitRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMenues() {
        //return Response.ok(oeffnungszeitRepository.getAllActiveOeffnungszeiten()).build();
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFreeTables(@PathParam("id") Long time_id, @QueryParam("date") String date ) {
        return Response.ok(oeffnungszeitRepository.getFreeTables(time_id, date)).build();
    }
}
