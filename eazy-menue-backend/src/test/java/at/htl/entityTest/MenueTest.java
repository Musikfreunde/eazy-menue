package at.htl.entityTest;

import at.htl.entities.Bestellung;
import at.htl.entities.Menue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenueTest {
    @Test
    public void t01_MenueTest_CreateMenueShouldBeEmpty(){
        Menue menue = new Menue();
        menue.setAppetizer("");
        menue.setDesert("");
        menue.setMainDish("");

        assertEquals(menue.getAppetizer(),0);
        assertEquals(menue.getDesert(),0);
        assertEquals(menue.getMainDish(),0);
    }

    @Test
    public void t02_MenueTest_CreateMenueShouldBeSchnitzel(){
        Menue menue = new Menue();
        menue.setMainDish("Schnitzel");

        assertEquals(menue.getMainDish(),"Schnitzel");
    }


}
