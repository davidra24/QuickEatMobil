package entity;

/**
 * Created by martin on 4/07/17.
 */

public class QuantityFood {
    private int quantity;
    private int food_id;

    public QuantityFood(int quantity, int food_id) {
        this.quantity = quantity;
        this.food_id = food_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
}
