package at.htl.serviceTest;

import at.htl.repositories.BestellungRepository;
import at.htl.repositories.MenueRepository;
import at.htl.repositories.OeffnungszeitenRepository;
import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MenueServiceTest {

    @Inject
    BestellungRepository bestellungRepository;

    @Inject
    MenueRepository menueRepository;

    @Inject
    OeffnungszeitenRepository oeffnungszeitenRepository;

    @Inject
    Logger logger;

    @Karate.Test
    Karate productTest() {
        return Karate.run("menue.feature").relativeTo(getClass());
    }
}
