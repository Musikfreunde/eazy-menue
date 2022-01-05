package at.htl.dtos;

public class MenueDTO {
    private Long id;

    private String date;

    private char code;

    private String appetizer;

    private String mainDish;

    private String dessert;

    private String categories;

    public MenueDTO() {
    }

    public MenueDTO(Long id, String date, char code, String appetizer, String mainDish, String desert, String categories) {
        this.id = id;
        this.date = date;
        this.code = code;
        this.appetizer = appetizer;
        this.mainDish = mainDish;
        this.dessert = desert;
        this.categories = categories;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }


}
