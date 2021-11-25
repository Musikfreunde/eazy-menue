package at.htl.repositories;

import at.htl.dtos.MenueDTO;
import at.htl.entities.Bestellung;
import at.htl.entities.Kantine;
import at.htl.entities.Menue;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@ApplicationScoped
public class MenueRepository implements PanacheRepository<Menue> {


    public List<MenueDTO> getAllMenues() {
        Query query = this.getEntityManager().createNamedQuery("Menue.getAllMenues",Object[].class);

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

        newMenue.setKantine(this.getEntityManager().createNamedQuery("Kantine.getActive", Kantine.class)
                .getSingleResult());

        try{
            Menue xd = this.getEntityManager().merge(newMenue);
            return true;
        }catch (Exception ex){
            return false;
        }

    }

    public boolean replaceMenue(MenueDTO menueDTO) {
        Menue menueFromDb = this.getEntityManager().createNamedQuery("Menue.getById", Menue.class)
                .setParameter("id", menueDTO.getId()).getSingleResult();


        menueFromDb.setAppetizer(menueDTO.getAppetizer());
        menueFromDb.setMainDish(menueDTO.getMainDish());
        menueFromDb.setDessert(menueDTO.getDessert());
        menueFromDb.setCode(menueDTO.getCode());

        try{
            this.getEntityManager().getTransaction().begin();
            this.getEntityManager().merge(menueFromDb);
            this.getEntityManager().getTransaction().commit();
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
