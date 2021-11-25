package at.htl.dtos;

public class OeffnungszeitenDTO {
    private String time;

    private boolean chosen;

    private Long id;

    private int maxSeats;

    private int freeSeats;

    public OeffnungszeitenDTO(String time, Long id, int maxSeats) {
        this.time = time;
        this.chosen = false;
        this.id = id;
        this.maxSeats = maxSeats;
        this.freeSeats = -1;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
