package at.htl.service;

import at.htl.dtos.MenueDTO;
import at.htl.repositories.MenueRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/menue/menues")
public class MenueService {
    @Inject
    private MenueRepository menueRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMenues() {
        return Response.ok(menueRepository.getAllMenues()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addMenue(MenueDTO menueDTO){
        return Response.ok(menueRepository.addMenue(menueDTO)).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response replaceMenue(MenueDTO menueDTO){
        return Response.ok(menueRepository.replaceMenue(menueDTO)).build();
    }
    @Path("/recommendation")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRecommendation(@QueryParam("date") String date,@QueryParam("name") String name) {
        return Response.ok(menueRepository.getRecommendation(name,date).getCode()).build();
    }

    @Path("/{date}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMenuesByDate(@PathParam("date") String date) {
        return Response.ok(menueRepository.getMenuesByDate(date)).build();
    }

}
