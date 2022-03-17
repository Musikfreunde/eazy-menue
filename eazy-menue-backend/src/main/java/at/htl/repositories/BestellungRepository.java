package at.htl.repositories;

import at.htl.dtos.*;
import at.htl.entities.Bestellung;
import at.htl.entities.Categories;
import at.htl.entities.Menue;
import at.htl.entities.Oeffnungszeit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
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
            this.getEntityManager().merge(temp);
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
                    (String) temp[3],((LocalDate) temp[4]).toString(), (Long) temp[5],(Integer) temp[6], (String) temp[7]);
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

        //Nimmt alle Kategorien, die der Benutzer bestellt hat und sortiert nach Anzahl
        Map<String,Long> fr = categories.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        var temp = fr.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return temp.keySet().stream().collect(Collectors.toList());
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

    public List<WeekDaysDTO> getDaysOfWeekByUser(String user) throws ParseException {
        List<BestellungHistoryDTO> orders = getOrdersOfUser(user);

        List<String> dates = new LinkedList<>();

        for (BestellungHistoryDTO o : orders){
            dates.add(o.getMenueDate());
        }

        HashMap<String, WeekDaysDTO> weekDays = new HashMap();


        weekDays.put("Montag",new WeekDaysDTO("Montag",0));
        weekDays.put("Dienstag", new WeekDaysDTO("Dienstag",0));
        weekDays.put("Mittwoch", new WeekDaysDTO("Mittwoch",0));
        weekDays.put("Donnerstag",new WeekDaysDTO("Donnerstag",0));

        for (String d : dates){
            Calendar c = Calendar.getInstance();

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);

            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 2){
                WeekDaysDTO week = weekDays.get("Montag");
                week.amount++;
                weekDays.replace("Montag",week);
            }
            if (dayOfWeek == 3){
                WeekDaysDTO week = weekDays.get("Dienstag");
                week.amount++;
                weekDays.replace("Dienstag",week);
            }
            if (dayOfWeek == 4){
                WeekDaysDTO week = weekDays.get("Mittwoch");
                week.amount++;
                weekDays.replace("Mittwoch",week);
            }
            if (dayOfWeek == 5){
                WeekDaysDTO week = weekDays.get("Donnerstag");
                week.amount++;
                weekDays.replace("Donnerstag",week);
            }
        }

        return weekDays.values().stream().collect(Collectors.toList());

    }
}
