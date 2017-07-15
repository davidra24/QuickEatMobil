package entity;

/**
 * Created by martin on 2/07/17.
 */

public class Food {
    private int id;
    private String features;
    private String name;
    private boolean availability;
    private int price;
    private int preparationTime;
    private int categoryId;

    public Food(int id, String features, String name, boolean availability, int price, int preparationTime, int categoryId) {
        this.id = id;
        this.features = features;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.preparationTime = preparationTime;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
