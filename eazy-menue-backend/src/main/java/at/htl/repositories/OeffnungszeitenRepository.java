package at.htl.repositories;

import at.htl.dtos.OeffnungszeitenDTO;
import at.htl.entities.Oeffnungszeit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OeffnungszeitenRepository {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ooevkantine");
    EntityManager entityManager = emf.createEntityManager();

    public List<OeffnungszeitenDTO> getAllActiveOeffnungszeiten() {
        Query query = entityManager.createNamedQuery("Oeffnungszeit.getTimesActive",Object[].class);

        return (List) query.getResultList().stream().map( m ->{
            Object[] temp = (Object[]) m;
            return new OeffnungszeitenDTO(temp[0] + " - " + temp[1], (Long)temp[2], (int)temp[3]);
        }).collect(Collectors.toList());
    }

    public Long getFreeTables(Long id, String date) {
        Query query = entityManager.createNamedQuery("Oeffnungszeit.getFreeSeatsForDateSlot")
                .setParameter("timeId",id).setParameter("date", LocalDate.parse(date));
        Query queryId = entityManager.createNamedQuery("Oeffnungszeit.getById", Oeffnungszeit.class)
                .setParameter("id",id);
        Oeffnungszeit time = (Oeffnungszeit) queryId.getSingleResult();
        return time.getMaxPositions() - (Long)query.getSingleResult();
    }
}
