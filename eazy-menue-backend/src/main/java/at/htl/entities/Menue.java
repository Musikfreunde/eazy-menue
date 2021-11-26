package at.htl.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "MENUE")
@NamedQueries({
        @NamedQuery(name="Menue.getAllMenues",
                query="SELECT m.id, m.date, m.code, m.appetizer, m.mainDish, m.desert FROM Menue m ORDER BY m.createdAt DESC"
        ),
        @NamedQuery(name="Menue.getById",
                query = "select m from Menue m where m.id = :id"
        )
})
public class Menue {
    @SequenceGenerator(name = "menue_seq_gen", sequenceName = "MENUE_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menue_seq_gen")
    @Id
    @Column(name = "MENUE_ID")
    private Long id;

    @Column(name = "MENUEDATUM")
    private LocalDate date;

    @Column(name = "MENUECODE", length = 1)
    private char code;

    @Column(name = "VORSPEISE", length = 2000)
    private String appetizer;

    @Column(name = "HAUPTSPEISE", length = 2000)
    private String mainDish;

    @Column(name = "NACHSPEISE", length = 2000)
    private String desert;

    @Column(name = "ANGELEGTUM",length = 6)
    private Timestamp createdAt;

    @Column(name = "ANGELEGTVON",length = 32)
    private String createdBy;

    @Column(name = "GEAENDERTUM",length = 6)
    private Timestamp changedAt;

    @Column(name = "GEAENDERTVON",length = 32)
    private String changedBy;

    @Column(name = "CATEGORIES",length = 255)
    private String categories;

    @ManyToOne
    @JoinColumn(name = "KANTINE_ID")
    private Kantine kantine;

    public Menue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(String appetizer) {
        this.appetizer = appetizer;
    }

    public String getMainDish() {
        return mainDish;
    }

    public void setMainDish(String mainDish) {
        this.mainDish = mainDish;
    }

    public String getDesert() {
        return desert;
    }

    public void setDessert(String desert) {
        this.desert = desert;
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

    public void setDesert(String desert) {
        this.desert = desert;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
