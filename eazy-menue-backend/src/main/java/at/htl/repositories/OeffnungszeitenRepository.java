package at.htl.repositories;

import at.htl.dtos.OeffnungszeitenDTO;
import at.htl.entities.Oeffnungszeit;
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
public class OeffnungszeitenRepository implements PanacheRepository<Oeffnungszeit> {
   public List<OeffnungszeitenDTO> getAllActiveOeffnungszeiten() {
        Query query = this.getEntityManager().createNamedQuery("Oeffnungszeit.getTimesActive",Object[].class);

        return (List) query.getResultList().stream().map( m ->{
            Object[] temp = (Object[]) m;
            return new OeffnungszeitenDTO(temp[0] + " - " + temp[1], (Long)temp[2], (int)temp[3]);
        }).collect(Collectors.toList());
    }

    public Long getFreeTables(Long id, String date) {
        Query query = this.getEntityManager().createNamedQuery("Oeffnungszeit.getFreeSeatsForDateSlot")
                .setParameter("timeId",id).setParameter("date", LocalDate.parse(date));
        Query queryId = this.getEntityManager().createNamedQuery("Oeffnungszeit.getById", Oeffnungszeit.class)
                .setParameter("id",id);
        Oeffnungszeit time = (Oeffnungszeit) queryId.getSingleResult();
        return time.getMaxPositions() - (Long)query.getSingleResult();
    }
}
