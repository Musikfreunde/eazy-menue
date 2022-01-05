package at.htl.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "BESTELLUNG")
@NamedQueries({
        @NamedQuery(name="Bestellung.getOrdersOfUser",
                query = "Select b.id, b.createdAt, b.orderedFor, b.menue.mainDish, b.menue.date, CONCAT(CONCAT(b.oeffnungszeit.timeWindowFrom ,' - '), b.oeffnungszeit.timeWindowTo) as timewindow  from Bestellung b  where b.orderedFor = :name and b.canceledAt IS NULL ORDER BY b.menue.date DESC "),
        @NamedQuery(name = "Bestellung.getBestellung",
                query = "select b from Bestellung b where b.id = :id"),
        @NamedQuery(name = "Bestellung.getOrdersByDate",
                query = "select b.menue.code ,CONCAT(CONCAT(b.oeffnungszeit.timeWindowFrom ,' - '), b.oeffnungszeit.timeWindowTo) as timewindow, b.orderedFor, b.menue.mainDish, b.menue.date, b.personalNumber, b.menueCounter from Bestellung b where b.menue.date = :date and b.canceledAt is null order by b.menue.code, b.orderedFor "),
        @NamedQuery(name = "Bestellung.getALlCategoriesByUsername",
                query = "select b.menue.categories from Bestellung b where b.orderedFor = :name group by b.menue.categories order by count(b) DESC"),
        @NamedQuery(name = "Bestellung.getALlCategories",
                query = "select DISTINCT(b.menue.categories) from Bestellung b "),
        @NamedQuery(name = "Bestellung.getALlCategoriesByUsernameForStats",
                query = "select b.menue.categories from Bestellung b where b.orderedFor = :name")
})

public class Bestellung {
    @SequenceGenerator(name = "bestellung_seq_gen", sequenceName = "BESTELLUNG_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bestellung_seq_gen")
    @Id
    @Column(name = "BESTELLUNG_ID")
    private Long id;

    @Column(name = "BESTELLTVON",length = 32)
    private String orderedBy;

    @Column(name = "BESTELLTFUER",length = 32)
    private String orderedFor;

    @Column(name = "KOMMENTAR",length = 4000)
    private String comment;

    @Column(name = "PERSONALNUMMER")
    private Long personalNumber;

    @Column(name = "ANZAHL")
    private int menueCounter;

    @Column(name = "STORNIERTUM",length = 6)
    private Timestamp canceledAt;

    @Column(name = "ABGERECHNETUM")
    private Timestamp settledAt;

    @Column(name = "KOSTENSTELLE")
    private Integer moneyPool;

    @Column(name = "ANGELEGTUM",length = 6)
    private Timestamp createdAt;

    @Column(name = "ANGELEGTVON",length = 32)
    private String createdBy;

    @Column(name = "GEAENDERTUM",length = 6)
    private Timestamp changedAt;

    @Column(name = "GEAENDERTVON",length = 32)
    private String changedBy;

    @ManyToOne
    @JoinColumn(name = "OEFFNUNGSZEIT_ID")
    private Oeffnungszeit oeffnungszeit;

    @ManyToOne
    @JoinColumn(name = "MENUE_ID")
    private Menue menue;

    public Bestellung() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getOrderedFor() {
        return orderedFor;
    }

    public void setOrderedFor(String orderedFor) {
        this.orderedFor = orderedFor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Long personalNumber) {
        this.personalNumber = personalNumber;
    }

    public int getMenueCounter() {
        return menueCounter;
    }

    public void setMenueCounter(int menueCounter) {
        this.menueCounter = menueCounter;
    }

    public Timestamp getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(Timestamp canceledAt) {
        this.canceledAt = canceledAt;
    }

    public Timestamp getSettledAt() {
        return settledAt;
    }

    public void setSettledAt(Timestamp settledAt) {
        this.settledAt = settledAt;
    }

    public Integer getMoneyPool() {
        return moneyPool;
    }

    public void setMoneyPool(Integer moneyPool) {
        this.moneyPool = moneyPool;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Oeffnungszeit getOeffnungszeit() {
        return oeffnungszeit;
    }

    public void setOeffnungszeit(Oeffnungszeit oeffnungszeit) {
        this.oeffnungszeit = oeffnungszeit;
    }

    public Menue getMenue() {
        return menue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return personalNumber == that.personalNumber && menueCounter == that.menueCounter && Objects.equals(id, that.id) && Objects.equals(orderedBy, that.orderedBy) && Objects.equals(orderedFor, that.orderedFor) && Objects.equals(comment, that.comment) && Objects.equals(canceledAt, that.canceledAt) && Objects.equals(settledAt, that.settledAt) && Objects.equals(moneyPool, that.moneyPool) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(changedAt, that.changedAt) && Objects.equals(changedBy, that.changedBy) && Objects.equals(oeffnungszeit, that.oeffnungszeit) && Objects.equals(menue, that.menue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderedBy, orderedFor, comment, personalNumber, menueCounter, canceledAt, settledAt, moneyPool, createdAt, createdBy, changedAt, changedBy, oeffnungszeit, menue);
    }

    public void setMenue(Menue menue) {
        this.menue = menue;
    }
}
