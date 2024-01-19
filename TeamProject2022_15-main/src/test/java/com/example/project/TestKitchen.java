package com.example.project;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestKitchen {

  private static Connection connection;

  @Test
  void testGetOrders(){
    Item item1 = new Item("potato", "none", 12);
    Item item2 = new Item("banana", "gluten", 69);
    Item item3 = new Item("salmon", "fish", 200);

    ArrayList<Item> items = new ArrayList<Item>();
    items.add(item1);
    items.add(item2);
    items.add(item3);

    ConnectToDatabase connect = new ConnectToDatabase();
    connection = connect.connect();

    connect.addOrder(connection, items,0);
    connect.addOrder(connection, items,1);
    connect.addOrder(connection, items,2);
    System.out.println(connect.getTableOrder(connection));
    assertEquals("potato,banana,salmon", connect.getTableOrder(connection).get(0).getItemStrings());
    connect.clearTable(connection, "orders");
  }
 
  // Won't run
  // @Test
  // void testExistingOrder() {
  //   List<Order> orders = new ArrayList<>();
  //   Order order = new Order(Status.IN_PROGRESS, 10, 123, null);
  //   orders.add(order);
    
  //   NotifyWaiter.ReadyOrder(orders);
    
  // }

}
