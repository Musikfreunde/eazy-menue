package at.htl.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/kantine")
public class KantineService {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hier gibts nichts.";
    }
}
