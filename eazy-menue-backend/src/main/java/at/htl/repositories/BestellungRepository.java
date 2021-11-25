package at.htl.repositories;


import at.htl.dtos.BestellungDTO;
import at.htl.dtos.BestellungHistoryDTO;
import at.htl.dtos.BestellungKantineDTO;
import at.htl.entities.Bestellung;
import at.htl.entities.Menue;
import at.htl.entities.Oeffnungszeit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class BestellungRepository {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ooevkantine");
    EntityManager entityManager = emf.createEntityManager();


    public List<BestellungHistoryDTO> getOrdersOfUser(String name) {
        Query query = entityManager.createNamedQuery("Bestellung.getOrdersOfUser", Object[].class).setParameter("name", name);

        return (List) query.getResultList().stream().map(m -> {
            Object[] temp = (Object[]) m;

            return new BestellungHistoryDTO((Long) temp[0], (Timestamp) temp[1], (String) temp[2],
                    (String) temp[3],((LocalDate) temp[4]).toString(), (String) temp[5]);
        }).collect(Collectors.toList());
    }

    public boolean addOrder(BestellungDTO bestellungDTO) {

        Bestellung newBestellung = new Bestellung();

        newBestellung.setOrderedBy(bestellungDTO.getOrderedBy());
        newBestellung.setOrderedFor(bestellungDTO.getOrderedFor());
        newBestellung.setComment(bestellungDTO.getComment());
        newBestellung.setMenue(entityManager.createNamedQuery("Menue.getById", Menue.class)
                .setParameter("id", bestellungDTO.getMenueId()).getSingleResult());
        newBestellung.setMenueCounter(bestellungDTO.getAmount());
        newBestellung.setOeffnungszeit(entityManager.createNamedQuery("Oeffnungszeit.getById", Oeffnungszeit.class)
                .setParameter("id", bestellungDTO.getTimeId()).getSingleResult());
        newBestellung.setPersonalNumber(bestellungDTO.getPersonalNummer());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(newBestellung);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderById(Long id) {
        try{
            Bestellung temp = entityManager.createNamedQuery("Bestellung.getBestellung", Bestellung.class)
                    .setParameter("id", id).getSingleResult();
            temp.setCanceledAt(new Timestamp(System.currentTimeMillis()));
            entityManager.getTransaction().begin();
            entityManager.merge(temp);
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List<BestellungKantineDTO> getOrdersByDate(String date) {
        LocalDate dateObject = LocalDate.parse(date);
        Query query = entityManager.createNamedQuery("Bestellung.getOrdersByDate", Object[].class)
                .setParameter("date", dateObject);
        return (List) query.getResultList().stream().map(m -> {
            Object[] temp = (Object[]) m;
            return new BestellungKantineDTO((char) temp[0], (String) temp[1], (String) temp[2],
                    (String) temp[3],((LocalDate) temp[4]).toString(), (Long) temp[5],(Integer) temp[6]);
        }).collect(Collectors.toList());

    }
}
