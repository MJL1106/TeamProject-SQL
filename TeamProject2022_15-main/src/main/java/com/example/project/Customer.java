package com.example.project;

import java.util.LinkedList;
import java.util.List;
import java.sql.*;

/**
 * Customer class currently used for allergies and calories in an order
 * 
 * @author Bruno Paiva Alves, Luke Newton
 *
 */

public class Customer {

    // Recreating an order
    private List<Item> orderItems;
    private List<String> allergyItems;
    private LinkedList<String> tableStatus;
    private LinkedList<String> helpStatus;
    private Item add;
    private int caloriesTotal;
    private ConnectToDatabase Connect;
    private int customerID;

    /**
     * Constructor for Customer
     */
    Customer() {
        customerID = 0;
        Connect = new ConnectToDatabase();
        orderItems = new LinkedList<>();
        allergyItems = new LinkedList<>();
    }

    /**gets customer ID 
     * @return Customer ID
    */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Function to find the number of tables as per the database
     * 
     * @param connection An active SQL connection
     * @return The number of tables
     */
    public int tableNumber(Connection connection) {
        return Connect.tableSize(connection);
    }

    /**
     * Gets the status of the table in the SQL table
     * 
     * @param connection An active SQL connection
     * @return A list of tables
     */
    public LinkedList<String> tableStatus(Connection connection) {
        tableStatus = Connect.tableStatus(connection);
        return tableStatus;
    }

    public LinkedList<String> getHelpStatus(Connection connection) {
        helpStatus = Connect.getHelpStatus(connection);
        return helpStatus;
    }

    public LinkedList<String> getTablePaid(Connection connection) {
        helpStatus = Connect.getTablePaid(connection);
        return helpStatus;
    }

    public void resetTable(Connection connection, int tableNumber) {
        Connect.resetTable(connection, tableNumber);
    }

    public void resetHelpStatus(Connection connection, int tableNumber) {
        Connect.resetHelpStatus(connection, tableNumber);
    }

    public void setTableStatus(Connection connection, int tableNumber, int orderNumber) {
        Connect.setTableStatus(connection, tableNumber, orderNumber);
    }

    /**
     * Change the status of a table
     * 
     * @param connection  An active SQL connection
     * @param tableNumber The table number
     * @param status      The string to put as status
     */
    public void changeTable(Connection connection, int tableNumber, String status) {
        Connect.changeTableDetails(connection, tableNumber, status);
    }

    public void changeTablePaid(Connection connection, int tableNumber) {
        Connect.changeTablePaid(connection, tableNumber);
    }

    public void changeTableHelp(Connection connection, int tableNumber) {
        Connect.changeHelpStatus(connection, tableNumber);
    }

    /**
     * Method takes an item and returns the type of Allegern if one
     * 
     * @param item is an item from the menu
     *
     * @return name of the allergen
     *
     */
    public String checkItemAllegries(Item item) {
        if (item.getAllergens().equals("")) {
            return "No Allergen";
        } else {
            return String.format("Allergen(s) :" + item.getAllergens());
        }
    }

    /**
     * Uses the checkItemAllergies on the entire order
     * 
     * @return a list of the order and any possible allergens
     */
    public List<String> checkOrderAllergies() {
        allergyItems.clear();
        for (int x = 0; x < orderItems.size(); x++) {
            allergyItems.add(String.format("%d. %s, %s\n", x, orderItems.get(x).getName(),
                    checkItemAllegries(orderItems.get(x))));
        }
        return allergyItems;
    }

    /**
     * Method adds an item to the order
     * 
     * @param name      the name of the item being added
     * @param allergens the allergens that the item contains
     * @param calories  the amount of calories that the item contains
     *
     * @return the items added to the order
     *
     */
    public String addItem(String name, String allergens, int calories) {
        add = new Item(name, allergens, calories);
        orderItems.add(add);
        return String.format("Added %s to order", add.getName());
    }

    /**
     * Clears all items in the order
     * 
     * @return A confirmation string
     */
    public String clearOrder() {
        if (orderItems.size() > 0) {
            orderItems.clear();
            return "Cleared current order";
        }
        return "Order was empty";
    }

    /**
     * Adds an item to the order
     * 
     * @param item item to be added
     * @return A confirmation message
     */
    public String addItem(Item item) {
        orderItems.add(item);
        return String.format("Added %s to order", item.getName());
    }

    /**
     * Shows the current order
     * 
     * @return The current order
     */
    public String showOrder() {
        String text = "Current order: \n";
        if (orderItems.size() > 0) {
            for (int x = 0; x < orderItems.size(); x++) {
                text += orderItems.get(x).getName()+"\n";
            }
        } else {
            text = "Empty order";
        }
        return text;
    }

    /**
     * Method calculates the total calories in a order
     * 
     * @return the total calories
     */
    public String checkOrderCalories() {
        if (orderItems.size() < 1) {
            return "no items";
        } else {
            caloriesTotal = 0;
            for (int x = 0; x < orderItems.size(); x++) {
                caloriesTotal += orderItems.get(x).getCalories();
            }
            return String.format("Total calories is: %d", caloriesTotal);
        }
    }

    /**
     * Adds the order to the database
     * 
     * @param connection An active SQL connection
     * @return A confirmation message or an error
     */
    public String createOrder(Connection connection) {
        // Everytime an order is created increment customer ID by 1
        customerID += 1;
        return Connect.addOrder(connection, orderItems, customerID);
    }

    /**
     * Adds order to the database with specific ID
     * 
     * @param id         the customer id to be used
     * @param connection An active SQL connection
     * @return A confirmation message or an error
     */
    public String createOrder(int id, Connection connection) {
        return Connect.addOrder(connection, orderItems, id);
    }

    /**
     * Updates the customer ID to the most recent one in the orders table
     * 
     * @param connection An active SQL connection
     * @return A confirmation message of the new Customer id
     */
    public String update(Connection connection) {
        customerID = Connect.getMax(connection, "cust_id", "orders");
        return "Customer id updated to " + customerID;
    }

    /**
     * Method to clear the orders table
     * 
     * @param connection An active SQL connection
     * @return A confirmation string or an error
     */
    // This method should probably be moved to the kitchen class in future
    public String clearTable(Connection connection) {
        return Connect.clearTable(connection, "orders");
    }
}
