package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class WaiterTest {

    private Waiter waiter;
    private ArrayList<Item> mockedList;
    private ArrayList<Item> mockedList2;
    private Item taco;
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    Menu menu;
    List<Item> menuList;
    private static Connection connection;


    @BeforeEach
    public void setup() {

        menu = new Menu();
        ConnectToDatabase connect = new ConnectToDatabase();
        connection = connect.connect();
        menu.createMenu(connection);
        menuList = menu.getMenu();

        waiter = new Waiter();
        mockedList = new ArrayList<Item>();
        taco = new Item("taco", "none", 300);

        // Creating a list to test the change menu functions with
        mockedList.add(taco);
        mockedList.add(new Item("2", "none", 10));
        mockedList.add(new Item("3", "none", 10));
        mockedList.add(new Item("4", "none", 10));

        // A second list to compare with the one above for testing
        mockedList2 = new ArrayList<Item>();
        mockedList2.add(new Item("1", "none", 10));
        mockedList2.add(new Item("2", "none", 10));
        mockedList2.add(new Item("3", "none", 10));
        
        System.setOut(new PrintStream(outContent));
    }

    // Test 1
    // Created removeItem() in Waiter.java
    @Test
    void testRemoveMockItem() {
        waiter.removeItem(mockedList, "taco");
        assertEquals(mockedList.size(), mockedList2.size());
    }

    // Test 2
    // Create addItem() in Waiter.java
    @Test
    void testAddMockItem() {
        waiter.addItem(mockedList2, taco);
        assertEquals(mockedList2.size(), mockedList.size());
    }

    // Test 3
    // tests if items are removed from the actual menu list
    @Test
    void testRemoveItem() {
        menuList = waiter.removeItem(menuList, "Crunchy taco");
        String line = menuList.get(0).toString();
        assertEquals(line, "Soft taco calories: 74 allergens: none",
            "The new first entry in the menu should be soft taco");
    }
    
    //Test 4
    //test if items in order list are displayed
    @Test
    void testDisplayOrder() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
        orders.add(order);
        Waiter.orderTimes(orders);
        assertEquals(outContent.toString(), 
        "123 12\r\n");
        
    }

    //Test 5
    //test if an order has been cancelled
    @Test
    void testCancelled() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
        orders.add(order);
        order = new Order(Status.IN_PROGRESS, 12, 124, null, 2);
        orders.add(order);
        Waiter.cancelOrder(orders, 123);
        assertEquals(orders.get(0).getStatus(), Status.CANCELLED);
    }

    //Test 6
    //test that cancelling an invalid order gives an error message.
    @Test
    void testInvalidCancel() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
        orders.add(order);
        Waiter.cancelOrder(orders, 128);
        assertEquals(outContent.toString(), "Order number 128 is invalid.\r\n");
    }


    //Test 7
    //test if items in ready to deliver items are displayed
    @Test
    void testDisplayReady() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.READY, 12, 123, null, 1);
        orders.add(order);
        Waiter.DisplayReady(orders);
        assertEquals(outContent.toString(), 
        "Order number 123 is ready to be delivered.\r\n");
        
    }
}
