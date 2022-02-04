package at.htl.repositoryTest;

import at.htl.dtos.MenueDTO;
import at.htl.entities.Menue;
import at.htl.repositories.MenueRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
public class MenueRepositoryTest {

    @Inject
    MenueRepository repository;

    @Transactional
    @Test
    public void t01_AddMenues(){
        repository.addMenue(new MenueDTO(0L,"2022-02-04",'A',"Suppe","Schnitzel","Apfel","Schwein"));
        var list = repository.getAllMenues();
        var item = list.get(0);

        assertThat(item.getMainDish()).isEqualTo("Schnitzel");
    }

    @Transactional
    @Test
    public void t02_AddMoreMenues(){
        var m1 = new MenueDTO(1L,"2022-02-04",'A',"Suppe","Schnitzel","Apfel","Schwein");
        var m2 = new MenueDTO(2L,"2022-02-04",'B',"Pudding","Schnitzel","Milka","Schwein");
        var m3 = new MenueDTO(3L,"2022-02-04",'C',"Apfel","Kartoffeln","Kuchen","Vegetarisch");
        var m4 = new MenueDTO(4L,"2022-02-04",'A',"Irgendwas","Schnitzel","Apfel","Schwein");

        repository.addMenue(m1);
        repository.addMenue(m2);
        repository.addMenue(m3);
        repository.addMenue(m4);

        List<MenueDTO> list = repository.getAllMenues();

        MenueDTO item1 = list.get(0);
        assertThat(item1.getAppetizer()).isEqualTo("Irgendwas");

        MenueDTO item2 = list.get(1);
        assertThat(item2.getAppetizer()).isEqualTo("Apfel");

        MenueDTO item3 = list.get(2);
        assertThat(item3.getAppetizer()).isEqualTo("Pudding");

        MenueDTO item4 = list.get(3);
        assertThat(item4.getAppetizer()).isEqualTo("Suppe");

    }

    @Transactional
    @Test
    public void t03_GetAll4MenuesFor02_05_2022(){

        var m1 = new MenueDTO(5L,"2022-02-06",'A',"Suppe","Schnitzel","Apfel","Schwein");
        var m2 = new MenueDTO(6L,"2022-02-06",'B',"Pudding","Schnitzel","Milka","Schwein");
        var m3 = new MenueDTO(7L,"2022-02-06",'C',"Apfel","Kartoffeln","Kuchen","Vegetarisch");
        var m4 = new MenueDTO(8L,"2022-02-05",'A',"Irgendwas","Schnitzel","Apfel","Schwein");

        if (m1.getId() == null && m2.getId() == null && m3.getId() == null && m4.getId() == null){
            repository.addMenue(m1);
            repository.addMenue(m2);
            repository.addMenue(m3);
            repository.addMenue(m4);
        }

        List<Menue> list = repository.getMenuesByDate("2022-02-06");

        assertThat(list.size()).isEqualTo(3);
    }

    @Transactional
    @Test
    public void t05_ReplaceMenue_NotWorking(){

        var m1 = new MenueDTO(900L,"2022-02-06",'A',"Suppe","Irgendwas","Apfel","Schwein");

        repository.addMenue(m1);

        var m2 = new MenueDTO(900L,"2022-02-06",'B',"Pudding","Schnitzel","Milka","Schwein");
        repository.replaceMenue(m2);

        assertThat(repository.findById(m1.getId()).getMainDish()).isEqualTo("Schnitzel");
    }
}
