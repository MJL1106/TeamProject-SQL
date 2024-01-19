package com.example.project;

import java.util.List;
import java.time.LocalDate;
/**
 * Represents a single order
 * Stores information about the order status, time of order and a list containing all order items
 *
 * @author Mia Marumoto Quinn
 */
public class Order {

    private Status status;
    private Object time;
    private int orderNum;
    private List<Item> orderItems; //Initialised in Customer.java when creating a new order object
    private int tableNum;

    private String timeString;
    private String itemStrings; //The items are listed as a String when being retrieved from the database

    /**
     * Constructor for the Order class
     *
     * @param status     the current status of the order
     * @param time       the time the order was made
     * @param orderNum   the order number
     * @param orderItems a list containing all items in the order
     */
    Order(Status status, Object time, int orderNum, List<Item> orderItems, int tableNum) {
        this.status = status;
        this.time = time;
        this.orderNum = orderNum;
        this.orderItems = orderItems;
        this.tableNum = tableNum;
    }

    /**
     * Generic constructor for the Order class
     */
    Order(){}

    /**
     * Sets the item's content (referring to the names of the items)
     * 
     * @param itemStrings The items as a string
     */
    public void setItemStrings(String itemStrings) {
        this.itemStrings = itemStrings;
    }

    /**
     * Sets the table number of an order
     * 
     * @param tableNum The table number
     */
    public void setTableNum(int tableNum){
        this.tableNum = tableNum;
    }

    /**
     * Sets the order number
     * 
     * @param orderNum The order number/customer id of the order
     */
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * Sets the status of the order
     *
     * @param status the current enum status of the order
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets the time of the order
     *
     * @param time the time the order was made
     */
    public void setTime(Object time) {
      LocalDate now = LocalDate.now();
      now = (LocalDate) time;
      this.time = now;
    }


    /**
     * Gets the time the order was made
     *
     * @return the time the order was made
     */
    public int getTime() {
        return (int) time;
    }

    /**
     * Gets the status of the order
     * 
     * @return status of the order
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the Items in the order
     * 
     * @return the current list of order items
     */
    public List<Item> getOrderItems() {
        return orderItems;
    }

    /**
     * Gets the order number
     * 
     * @return The order number
     */
    public int getOrderNum() {
        return orderNum;
    }

    /**
     * Sets the time as a string
     * 
     * @param time the String representation of the time
     */
    public void setTimeString(String time) {
        this.timeString = time;
    }

    /**
     * Gets the String representation of time as set in "setTimeString"
     * 
     * @return The string representation of the time the order was set
     */
    public String getTimeString() {
        return this.timeString;
    }

    /**
     * Gets the string representation of the Items as set in setItemStrings and removes special characters
     * namely: [&lt;&gt;(){}]
     * 
     * @return The String representation of the item with special characters being removed
     */
    public String getItemStrings() {
        itemStrings = itemStrings.replaceAll("[<>\\[\\](){}]","");
        return itemStrings;
    }
}
