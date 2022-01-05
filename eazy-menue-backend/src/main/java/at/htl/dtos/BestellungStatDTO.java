package at.htl.dtos;

public class BestellungStatDTO {

    public String category;

    public int amount;

    public BestellungStatDTO(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
