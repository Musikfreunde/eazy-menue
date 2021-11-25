package at.htl.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "KANTINE")
@NamedQueries(
        {
                @NamedQuery(name = "Kantine.getActive", query = "Select k from Kantine k where k.status = 'A'")
        }
)
public class Kantine {
    @Id
    @Column(name = "KANTINE_ID")
    private Long id;

    @Column(name = "KANTINENBEZEICHNUNG", length = 200)
    private String canteenDesc;

    @Column(name = "SERVICEBEZEICHNUNG", length = 200)
    private String serviceDesc;

    @Column(name = "STATUSKZ", length = 1)
    private char status;

    @Column(name = "ANGELEGTUM",length = 6)
    private Timestamp createdAt;

    @Column(name = "ANGELEGTVON",length = 32)
    private String createdBy;

    @Column(name = "GEAENDERTUM",length = 6)
    private Timestamp changedAt;

    @Column(name = "GEAENDERTVON",length = 32)
    private String changedBy;


    public Kantine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanteenDesc() {
        return canteenDesc;
    }

    public void setCanteenDesc(String canteenDesc) {
        this.canteenDesc = canteenDesc;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
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
}
