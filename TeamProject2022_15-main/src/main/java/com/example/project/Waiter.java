package com.example.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Represents a single waiter
 *
 * @author Mia Marumoto Quinn, Philippe Pfeiffer, Matthew Lowe
 */
public class Waiter {

  /**
   * Called when there a need to remove an item off the menu
   *
   * @param menu the menu originally shown to customers
   * @param name the name of the item to remove from the menu
   * @return the new menu after removing the item
   */
  public List<Item> removeItem(List<Item> menu, String name) {
    Predicate <Item> condition = item -> item.getName().equals(name);
    menu.removeIf(condition);
    return menu;
  }

  /**
   * Called when there a need to add an item to the menu
   *
   * @param menu the menu originally shown to customers
   * @param item the item to add to the menu
   * @return the new menu after adding the item
   */
  public List<Item> addItem(List<Item> menu, Item item) {
    menu.add(item);
    return menu;
  }

  /**
   * Method notifies the waiter that an order is ready.
   * 
   * @param Order is the ready order number chosen by the kitchen
   */
  public static void Notification(String Order) {
    System.out.println("Order number " + Order + " is ready to be delivered.");
  }

  /**
   * Method lets the waiter see all ready orders.
   * 
   * @param orders is a list of orders.
   */
  public static void DisplayReady(List<Order> orders) {
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getStatus() == Status.READY) {
        Waiter.Notification(orders.get(i).getOrderNum() + "");
      }
    }
  }

  /** 
   * Method displays orders by the time they were placed.
   * 
   * @param orders is a list of orders. 
   */
  public static void orderTimes(List<Order> orders){
    for (int i = 0; i < orders.size(); i++) {
      System.out.println(orders.get(i).getOrderNum() + " " + orders.get(i).getTime());
    }
  }

  /**
   * Called when a list of all tables need to be added.
   *
   * @param size the amount of how many tables there are
   * @return a list of tables which are all available
   */
  public static ArrayList<Integer> createTableList(Integer size) {
    ArrayList<Integer> tableList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      tableList.add(0);
    }
    return tableList;
  }
  
  /**
   * Used to assignment people to tables.
   *
   * @param tableList a list of all the tables and if they are available (using 0s and 1s)
   */
  public static void updateTableList(ArrayList<Integer> tableList) {
    for (int i = 0; i < tableList.size(); i++) {
      if (tableList.get(i) != 1) {
        System.out.println("Table " + (i+1) + " is available.");
      }
    } Scanner sc = new Scanner(System.in);
    System.out.println("Which table is being assigned to?");
    String response = sc.nextLine();
    try {
      int index = Integer.parseInt(response);
      tableList.set(index-1, 1);
    } catch (Exception e) {
      System.out.println(e);
    } sc.close();
  }

  /** 
   * Method sets the status of an order to cancelled.
   * 
   * @param orders is a list of orders
   * @param orderNumber the order to be cancelled
   */
  public static void cancelOrder(List<Order> orders, int orderNumber){
    boolean found = false;
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getOrderNum() == orderNumber){
        found = true;
        orders.get(i).setStatus(Status.CANCELLED);
        System.out.println("Order number " + orderNumber + " has been cancelled.");
      }
    }
    if (found == false){
      System.out.println("Order number " + orderNumber + " is invalid.");
    }

  }

  /** 
   * Method allows to change the status of an order by a waiter.
   * 
   * @param orders is a list of orders. 
   */
  public static void ChangeStatus(List<Order> orders) {
    for (int i = 0; i < orders.size(); i++) {
      System.out.println(orders.get(i).getOrderNum());
    }
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Order Number: ");
    String strReadyON = scanner.nextLine(); 
    System.out.println("");
    try {
        int readyON = Integer.parseInt(strReadyON);
        boolean found = false;
        for (int i = 0; i < orders.size(); i++) {
            if (readyON == orders.get(i).getOrderNum()) {
              System.out.print("Enter Status: ");
              strReadyON = scanner.nextLine(); 
              System.out.println("");
              strReadyON = strReadyON.toUpperCase();
              switch(strReadyON){
                case "RECEIVED":
                  orders.get(i).setStatus(Status.ORDER_RECEIVED);
                  break;
                case "UNPAID":
                  orders.get(i).setStatus(Status.UNPAID);
                  break;
                case "COOKED":
                  orders.get(i).setStatus(Status.COOKED);
                  break;
                case "CANCELLED":
                  orders.get(i).setStatus(Status.CANCELLED);
                  break;
                case "IN_PROGRESS":
                  orders.get(i).setStatus(Status.IN_PROGRESS);
                  break;
                case "READY":
                  orders.get(i).setStatus(Status.READY);
                  break;
                case "DELIVERED":
                  orders.get(i).setStatus(Status.DELIVERED);
                  break;
              } 
              found = true;
            }
        } 
        if (found != true) {
            System.out.println("Order does not exist.");
        } else {
          Waiter.Notification(strReadyON);
        }
    }
    catch (Exception e) {
        System.out.println(e);
    }
    scanner.close();
}

}
