package com.example.project;

/**
 * Class to represent an Item
 */
public class Item {
    private String allergens;
    private String name;
    private String price;
    // The type of item (filling, side, etc)
    private String type;
    private int calories;
    private boolean in_stock;

    /**
     * Constructs an item
     * 
     * @param name      name of item
     * @param allergens allergens in the item
     * @param price     price of the item
     */
    Item(String name, String allergens, String price) {
        this.name = name;
        this.allergens = allergens;
        this.price = price;
    }

    /**
     * Constructs an item
     * 
     * @param name      name of item
     * @param allergens allergens in the item
     * @param price     price of the item
     * @param calories  calories of the item
     * @param type      The type of the item
     */
    Item(String name, String allergens, String price,  int calories, String type, Boolean in_stock) {
        this.name = name;
        this.allergens = allergens;
        this.price = price;
        this.calories = calories;
        this.type = type;
        this.in_stock = in_stock;
    }

    /**
     * Constructs an item
     * 
     * @param name      name of an item
     * @param allergens allergens in the item
     * @param calories  calories of the item
     */
    Item(String name, String allergens, int calories) {
        this.name = name;
        this.calories = calories;
        this.allergens = allergens;
    }

    /**
     * Getter method for allergen
     * @return The allergen
     */
    public String getAllergens() {
        return allergens;
    }

    /**
     * Getter method for calories
     * @return The calories value
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Getter method for name of item
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for stock of an item
     * @return The item's stock status
     */
    public boolean getInStock() {
        return in_stock;
    }

    /**
     * Getter method for price of an item
     * @return The price of an item
     */
    public String getPrice() {
        return price;
    }

    /**
     * Get's the type of the Item
     * @return the type
     */
    public String getType() {return type;};

    /**
     * Set's the item's type
     * @param type the type the item should be
     */
    public void setType(String type){
        this.type = type;
    }

    // Added an equals function since it was useful for a test
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item object = (Item) obj;
            if ((object.getCalories() == this.calories) && (object.getName().equals(this.name)) && (object.getAllergens().equals(this.allergens))
                    && (object.getType().equals(this.type)) && (object.getPrice().equals(this.price))) {
                return true;
            }
        }
        return false;
    }
    

    @Override
    public String toString() {
        return name + " calories: " + calories + " allergens: " + allergens;
    }
}
