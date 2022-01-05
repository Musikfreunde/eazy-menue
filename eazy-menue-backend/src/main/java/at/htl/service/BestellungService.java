package at.htl.service;

import at.htl.dtos.BestellungDTO;
import at.htl.repositories.BestellungRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/menue/bestellung")
public class BestellungService {
    @Inject
    BestellungRepository bestellungRepository;

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOrdersOfUser(@PathParam("name") String name) {
        return Response.ok(bestellungRepository.getOrdersOfUser(name)).build();
    }

    @GET
    @Path("/categories/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getALlCategoriesByUsername(@PathParam("name") String name) {
        return Response.ok(bestellungRepository.getALlCategoriesByUsername(name)).build();
    }

    @GET
    @Path("/categories")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getALlCategories() {
        return Response.ok(bestellungRepository.getALlCategories()).build();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOrdersByDate(@QueryParam("date") String date) {
        return Response.ok(bestellungRepository.getOrdersByDate(date)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addOrder(BestellungDTO bestellungDTO){
        return Response.ok(bestellungRepository.addOrder(bestellungDTO)).build();
    }

    @GET
    @Path("/stats/categories/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getALlCategoriesByUsernameForStats(@PathParam("name") String name) {
        return Response.ok(bestellungRepository.getALlCategoriesByUsernameForStats(name)).build();
    }

}
