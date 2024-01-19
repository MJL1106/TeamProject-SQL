package com.example.project;

import java.sql.*;
import java.util.ArrayList;

/**
 * Allows the kitchen to view order times, notify waiters about the current
 * order status, and set the status of the order.
 *
 * @author Mia Marumoto Quinn, Luke Newton
 */
public class Kitchen {

    private int orderNum;
    private ConnectToDatabase Connect;
    private Connection connection;

    Kitchen(Connection connection) {
        Connect = new ConnectToDatabase();
        this.connection = connection;
        orderNum = Connect.getMax(connection, "cust_id", "orders");
    }

    /**
     * This function confirms to the Kitchen staff that an order has been recieved
     * 
     * @return Confirmation of the order
     */
    public String confirmOrder() {
        int currentNum = orderNum;
        orderNum = Connect.getMax(connection, "cust_id", "orders");
        for (int x = currentNum+1; x < orderNum+1; x ++){
            Connect.changeOrderStatus(connection, x, Status.ORDER_RECEIVED);
        }
        String confirm = String.format("Confirmed a total of %d new orders from ID: %d to ID: %d", (orderNum - currentNum), currentNum, orderNum);
        return confirm;
    }

    /**
     * Changes the order status
     *
     * @return The status of the order
     */
    public String changeOrderStatus(int id, Status status) {
        return Connect.changeOrderStatus(connection, id, status);
    }

    /**
     * Shows the full order table
     * 
     * @return The order table
     */
    public ArrayList<Order> getOrders() {
        return Connect.getTableOrder(connection);
    }

    /**
     * get's a specific order
     * @param customerID The customer's ID
     * @return The order
     */
    public String getOrderStatus(int customerID){
        return Connect.getOrderStatus(connection, customerID);
    }

    /**
     * Gets the order Number, used mainly in the web page
     * @return the current highest orderNumber
     */
    public int getOrderNum(){
        return orderNum;
    }

}
