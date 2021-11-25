package at.htl.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "OEFFNUNGSZEIT")
@NamedQueries({
        @NamedQuery(name = "Oeffnungszeit.getTimesActive",
                query = "select o.timeWindowFrom, o.timeWindowTo, o.id, o.maxPositions from Oeffnungszeit o where o.status = 'A'"),

        @NamedQuery(name = "Oeffnungszeit.getById",
                query = "Select o from Oeffnungszeit o where o.id = :id"),
        @NamedQuery(name = "Oeffnungszeit.getFreeSeatsForDateSlot",
                query = "select count(b.id) from Bestellung b where b.menue.date = :date and b.oeffnungszeit.id = :timeId and b.canceledAt is null")
})
public class Oeffnungszeit {
    @Id
    @Column(name = "OEFFNUNGSZEIT_ID")
    private Long id;

    @Column(name = "STATUSKZ", length = 1)
    private char status;

    @Column(name = "ZEITFESTERVON", length = 5)
    private String timeWindowFrom;

    @Column(name = "ZEITFESTERBIS", length = 5)
    private String timeWindowTo;

    @Column(name = "MAXPLAETZE", nullable = true)
    private Integer maxPositions;

    @Column(name = "ANGELEGTUM",length = 6)
    private Timestamp createdAt;

    @Column(name = "ANGELEGTVON",length = 32)
    private String createdBy;

    @Column(name = "GEAENDERTUM",length = 6)
    private Timestamp changedAt;

    @Column(name = "GEAENDERTVON",length = 32)
    private String changedBy;

    @ManyToOne
    @JoinColumn(name = "KANTINE_ID")
    private Kantine kantine;

    public Oeffnungszeit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getTimeWindowFrom() {
        return timeWindowFrom;
    }

    public void setTimeWindowFrom(String timeWindowFrom) {
        this.timeWindowFrom = timeWindowFrom;
    }

    public String getTimeWindowTo() {
        return timeWindowTo;
    }

    public void setTimeWindowTo(String timeWindowTo) {
        this.timeWindowTo = timeWindowTo;
    }

    public Integer getMaxPositions() {
        return maxPositions;
    }

    public void setMaxPositions(Integer maxPositions) {
        this.maxPositions = maxPositions;
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

    public Kantine getKantine() {
        return kantine;
    }

    public void setKantine(Kantine kantine) {
        this.kantine = kantine;
    }
}
