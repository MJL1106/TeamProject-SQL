package com.example.project;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests for the customer connection
 */

@TestMethodOrder(OrderAnnotation.class)
public class CustomerConnectionTest {
    

    private static Connection connection;
    private static Customer customer;
    private static Item potato;

    @BeforeAll
    public static void setup(){
        customer = new Customer();
        potato = new Item("potato", "gluten?", 200 );
        ConnectToDatabase connect = new ConnectToDatabase();
        connection = connect.connect();
        customer.clearTable(connection);
    }

    // Honestly this one is more set up than a test >->
    @Test
    void MakeMenuJSON() {
        ConnectToDatabase.createMenuJson(connection);
    }
    

    // Testing to see if creating an order works and affects the table
    @Test
    @Order(1)
    void testCreateOrder() {
        customer.clearOrder();
        customer.addItem(potato);
        assertEquals("Created unpaid order number 1 with details: \n'{{ potato }}'", customer.createOrder(1, connection));
    }

    // Testing to see if creating an order works with several items
    @Test
    @Order(2)
    void testCreateOrderSeveral() {
        customer.clearOrder();
        customer.addItem(potato);
        customer.addItem(potato);
        assertEquals("Created unpaid order number 2 with details: \n'{{ potato },{ potato }}'", customer.createOrder(2, connection));
    }

    //Testing the update function
    @Test
    @Order(3)
    void testUpdate() {
        assertEquals("Customer id updated to 2", customer.update(connection));
    }

    // Creating an order without specifying ID
    @Test
    @Order(4)
    void testCreateOrderNoID() {
        customer.clearOrder();
        customer.update(connection);
        customer.addItem(potato);
        assertEquals("Created unpaid order number 3 with details: \n'{{ potato }}'", customer.createOrder(connection));
    }

    // Testing clearing the orders table
    @Test
    @Order(5)
    void testClearTable() {
        assertEquals("cleared table DELETE FROM orders WHERE true;", customer.clearTable(connection));
    }
}
