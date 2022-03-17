package at.htl.dtos;

public class BestellungKantineDTO {
    private char code;
    private String orderedFor;
    private String menue;
    private String date;
    private String timewindow;
    private Long personalNumber;
    private int menueCounter;
    private String comment;

    public BestellungKantineDTO(char code, String timewindow, String orderedFor, String menue, String date, Long personalNumber, int menueCounter, String comment ) {
        this.code = code;
        this.orderedFor = orderedFor;
        this.menue = menue;
        this.date = date;
        this.timewindow = timewindow;
        this.personalNumber = personalNumber;
        this.menueCounter = menueCounter;
        this.comment = comment == null ? "" : comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMenueCounter() {
        return menueCounter;
    }

    public void setMenueCounter(int menueCounter) {
        this.menueCounter = menueCounter;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getOrderedFor() {
        return orderedFor;
    }

    public void setOrderedFor(String orderedFor) {
        this.orderedFor = orderedFor;
    }

    public String getMenue() {
        return menue;
    }

    public void setMenue(String menue) {
        this.menue = menue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimewindow() {
        return timewindow;
    }

    public void setTimewindow(String timewindow) {
        this.timewindow = timewindow;
    }

    public Long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Long personalNumber) {
        this.personalNumber = personalNumber;
    }
}
