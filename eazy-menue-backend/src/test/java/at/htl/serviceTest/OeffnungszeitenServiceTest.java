package at.htl.serviceTest;

import at.htl.repositories.BestellungRepository;
import at.htl.repositories.MenueRepository;
import at.htl.repositories.OeffnungszeitenRepository;
import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@QuarkusTest
public class OeffnungszeitenServiceTest {

    @Inject
    BestellungRepository bestellungRepository;

    @Inject
    MenueRepository menueRepository;

    @Inject
    OeffnungszeitenRepository oeffnungszeitenRepository;

    @Inject
    Logger logger;

    @BeforeEach
    @Transactional
    void beforeEach() {

    }

    @Karate.Test
    Karate testGetStatistics() {
        return Karate.run("oeffnungszeit.feature").relativeTo(getClass());
    }
}
