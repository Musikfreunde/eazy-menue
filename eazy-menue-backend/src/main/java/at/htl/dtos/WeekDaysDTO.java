package at.htl.dtos;

public class WeekDaysDTO {

    public String weekday;

    public int amount;

    public WeekDaysDTO(String weekday, int amount) {
        this.weekday = weekday;
        this.amount = amount;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
