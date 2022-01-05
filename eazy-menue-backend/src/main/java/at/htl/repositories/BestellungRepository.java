package at.htl.repositories;

import at.htl.dtos.BestellungDTO;
import at.htl.dtos.BestellungHistoryDTO;
import at.htl.dtos.BestellungKantineDTO;
import at.htl.dtos.BestellungStatDTO;
import at.htl.entities.Bestellung;
import at.htl.entities.Categories;
import at.htl.entities.Menue;
import at.htl.entities.Oeffnungszeit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@ApplicationScoped
public class BestellungRepository implements PanacheRepository<Bestellung> {

    public List<BestellungHistoryDTO> getOrdersOfUser(String name) {
        Query query = this.getEntityManager().createNamedQuery("Bestellung.getOrdersOfUser", Object[].class).setParameter("name", name);

        return (List) query.getResultList().stream().map(m -> {
            Object[] temp = (Object[]) m;

            return new BestellungHistoryDTO((Long) temp[0], (Timestamp) temp[1], (String) temp[2], (String) temp[3],((LocalDate) temp[4]).toString(), (String) temp[5]);
        }).collect(Collectors.toList());

    }

    public boolean addOrder(BestellungDTO bestellungDTO) {

        Bestellung newBestellung = new Bestellung();

        newBestellung.setOrderedBy(bestellungDTO.getOrderedBy());
        newBestellung.setOrderedFor(bestellungDTO.getOrderedFor());
        newBestellung.setComment(bestellungDTO.getComment());

        newBestellung.setMenue(this.getEntityManager().createNamedQuery("Menue.getById", Menue.class).setParameter("id", bestellungDTO.getMenueId()).getSingleResult());

        newBestellung.setMenueCounter(bestellungDTO.getAmount());
        newBestellung.setOeffnungszeit(this.getEntityManager().createNamedQuery("Oeffnungszeit.getById", Oeffnungszeit.class).setParameter("id", bestellungDTO.getTimeId()).getSingleResult());
        newBestellung.setPersonalNumber(bestellungDTO.getPersonalNummer());
        try {
            persist(newBestellung);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderById(Long id) {
        try{
            Bestellung temp = this.getEntityManager().createNamedQuery("Bestellung.getBestellung", Bestellung.class)
                    .setParameter("id", id).getSingleResult();
            temp.setCanceledAt(new Timestamp(System.currentTimeMillis()));
            this.getEntityManager().getTransaction().begin();
            this.getEntityManager().merge(temp);
            this.getEntityManager().getTransaction().commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List<BestellungKantineDTO> getOrdersByDate(String date) {
        LocalDate dateObject = LocalDate.parse(date);
        Query query = this.getEntityManager().createNamedQuery("Bestellung.getOrdersByDate", Object[].class)
                .setParameter("date", dateObject);
        return (List) query.getResultList().stream().map(m -> {
            Object[] temp = (Object[]) m;
            return new BestellungKantineDTO((char) temp[0], (String) temp[1], (String) temp[2],
                    (String) temp[3],((LocalDate) temp[4]).toString(), (Long) temp[5],(Integer) temp[6]);
        }).collect(Collectors.toList());

    }

    public List<String> getALlCategoriesByUsername(String name) {
        TypedQuery<String> query = this.getEntityManager().createNamedQuery("Bestellung.getALlCategoriesByUsername", String.class)
                .setParameter("name", name);
        List<String> categories = new LinkedList<>();
        query.getResultList().forEach(c -> {
            if (c.contains(";")){
                String[] categoriesArray = c.split(";");
                categories.addAll(Arrays.asList(categoriesArray));
            }
            else{categories.add(c);}
        });
        return categories;
    }

    public List<String> getALlCategories(){
        return Stream.of(Categories.values()).map(Categories::name).collect(Collectors.toList());
    }

    public List<BestellungStatDTO> getALlCategoriesByUsernameForStats(String name) {

        List<String> distCat = getALlCategoriesByUsername(name);
        List<BestellungStatDTO> stats = new LinkedList<>();

        TypedQuery<String> query = this.getEntityManager().createNamedQuery("Bestellung.getALlCategoriesByUsernameForStats", String.class)
                .setParameter("name", name);
        List<String> categories = new LinkedList<>();
        query.getResultList().forEach(c -> {
            if (c.contains(";")){
                String[] categoriesArray = c.split(";");
                categories.addAll(Arrays.asList(categoriesArray));
            }
            else{categories.add(c);}
        });

        for (String s : distCat){
            int count = 0;
            for (String cat : categories){
                if (cat.equals(s)){
                    count++;
                }
            }
            stats.add(new BestellungStatDTO(s,count));
        }


        return stats;

        /*Query query  = this.getEntityManager().createNamedQuery("Bestellung.getALlCategoriesByUsernameForStats", Object[].class)
                .setParameter("name", name);

        return (List) query.getResultList().stream().map(m -> {
            Object[] temp = (Object[]) m;

            return new BestellungStatDTO(temp[0].toString(),(Long) temp[1]);
        }).collect(Collectors.toList());*/

    }
}
