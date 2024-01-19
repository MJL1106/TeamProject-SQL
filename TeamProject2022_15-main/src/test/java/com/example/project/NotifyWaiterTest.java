package com.example.project;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class NotifyWaiterTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    public void setup() {
        
        System.setOut(new PrintStream(outContent));
    }

    //Test 1
    //test if items in order list are displayed.
    @Test
    void testDisplayOrder() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
        orders.add(order);
        NotifyWaiter.getOrderTime(orders);
        assertEquals(outContent.toString(), 
        "Order Num: 123 Order time: 12\r\n");
    }

    //Test 2
    //test if multiple items in order list can be displayed.
    @Test
    void testMultipleDisplay() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 11, 124, null, 2);
        orders.add(order);
        order = new Order(Status.IN_PROGRESS, 12, 123, null, 3);
        orders.add(order);
        NotifyWaiter.getOrderTime(orders);
        assertEquals(outContent.toString(), 
        "Order Num: 124 Order time: 11\r\nOrder Num: 123 Order time: 12\r\n");
    }

    //Test 3
    //test if multiple items in order list can be displayed in chronological order.
    @Test
    void testSortDisplay() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
        orders.add(order);
        order = new Order(Status.IN_PROGRESS, 11, 124, null, 2);
        orders.add(order);
        order = new Order(Status.IN_PROGRESS, 10, 125, null, 3);
        orders.add(order);
        order = new Order(Status.IN_PROGRESS, 13, 126, null, 4);
        orders.add(order);
        NotifyWaiter.getOrderTime(orders);
        assertEquals(outContent.toString(), 
        "Order Num: 125 Order time: 10\r\n" +
        "Order Num: 124 Order time: 11\r\n" +
        "Order Num: 123 Order time: 12\r\n" +
        "Order Num: 126 Order time: 13\r\n");
    }
}
