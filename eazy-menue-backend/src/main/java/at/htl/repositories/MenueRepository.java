package at.htl.repositories;

import at.htl.dtos.MenueDTO;
import at.htl.entities.Kantine;
import at.htl.entities.Menue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MenueRepository {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ooevkantine");
    EntityManager entityManager = emf.createEntityManager();


    public List<MenueDTO> getAllMenues() {
        Query query = entityManager.createNamedQuery("Menue.getAllMenues",Object[].class);

        return (List) query.getResultList().stream().map( m ->{
            Object[] temp = (Object[]) m;
            return new MenueDTO((Long)temp[0],((LocalDate)temp[1]).toString(),(char)temp[2],(String) temp[3],
                    (String)temp[4],(String)temp[5]);
        }).collect(Collectors.toList());
    }

    public boolean addMenue(MenueDTO menueDTO) {

        Menue newMenue = new Menue();

        newMenue.setAppetizer(menueDTO.getAppetizer());
        newMenue.setMainDish(menueDTO.getMainDish());
        newMenue.setDessert(menueDTO.getDessert());
        newMenue.setDate(LocalDate.parse(menueDTO.getDate()));
        newMenue.setCode(menueDTO.getCode());

        newMenue.setKantine(entityManager.createNamedQuery("Kantine.getActive", Kantine.class)
                .getSingleResult());

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(newMenue);
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception ex){
            return false;
        }

    }

    public boolean replaceMenue(MenueDTO menueDTO) {
        Menue menueFromDb = entityManager.createNamedQuery("Menue.getById", Menue.class)
                .setParameter("id", menueDTO.getId()).getSingleResult();


        menueFromDb.setAppetizer(menueDTO.getAppetizer());
        menueFromDb.setMainDish(menueDTO.getMainDish());
        menueFromDb.setDessert(menueDTO.getDessert());
        menueFromDb.setCode(menueDTO.getCode());

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(menueFromDb);
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
