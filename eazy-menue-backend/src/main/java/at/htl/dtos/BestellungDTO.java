package at.htl.dtos;

public class BestellungDTO {
    private Long id;

    private String orderedBy;


    private int amount;

    private String comment;

    private Long menueId;

    private String orderedFor;

    private Long timeId;

    private Long personalNummer;

    public BestellungDTO() {
    }

    public BestellungDTO(String orderedBy, Long id, Long timeId, int amount, String orderedFor, String comment, Long orderId, Long personalNummer) {
        this.orderedBy = orderedBy;
        this.id = id;
        this.amount = amount;
        this.orderedFor = orderedFor;
        this.comment = comment;
        this.menueId = orderId;
        this.timeId = timeId;
        this.personalNummer = personalNummer;
    }

    public Long getPersonalNummer() {
        return personalNummer;
    }

    public void setPersonalNummer(Long personalNummer) {
        this.personalNummer = personalNummer;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Long getMenueId() {
        return menueId;
    }

    public void setMenueId(Long menueId) {
        this.menueId = menueId;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
}
