package at.htl.entityTest;

import at.htl.entities.Bestellung;
import at.htl.entities.Menue;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class BestellungTest {
    @Test
    public void t01_BestellungTest_CreateBestellungShouldBeNull(){
        Bestellung bestellung = new Bestellung();

        assertEquals(bestellung.getCanceledAt(),null);
        assertEquals(bestellung.getId(),null);
    }

    @Test
    public void t02_BestellungTest_CreateBestellungMenueShouldNotBeNull(){
        Bestellung bestellung = new Bestellung();
        bestellung.setMenue(new Menue());

        assertNotEquals(bestellung.getMenue(),null);
    }
}
