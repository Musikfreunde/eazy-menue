package at.htl.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class BestellungHistoryDTO {
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    private String orderedFor;

    private  String menueName;

    private String menueDate;

    private String timeWindow;

    public BestellungHistoryDTO(Long id, Timestamp createdAt, String orderedFor, String menueName, String menueDate,String window) {
        createdAt.setHours(createdAt.getHours() + 2);
        this.id = id;
        this.createdAt = createdAt;
        this.orderedFor = orderedFor;
        this.menueName = menueName;
        this.menueDate = menueDate;
        this.timeWindow = window;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }


    public String getMenueName() {
        return menueName;
    }

    public void setMenueName(String menueName) {
        this.menueName = menueName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderedFor() {
        return orderedFor;
    }

    public void setOrderedFor(String orderedFor) {
        this.orderedFor = orderedFor;
    }

    public String getMenueDate() {
        return menueDate;
    }

    public void setMenueDate(String menueDate) {
        this.menueDate = menueDate;
    }
}
