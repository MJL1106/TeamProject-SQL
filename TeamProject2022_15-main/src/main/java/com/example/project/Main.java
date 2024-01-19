package com.example.project;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mia Marumoto Quinn, Philippe Pfeiffer
 */
public class Main {

  private static Scanner scanner;

  /**
   * Function to end main when needed
   */
  public static void quit() {
    scanner.close();
    System.exit(1);
  }

  /**
   * adding things from menu to the customer
   * 
   * @param choice   the menu option entered
   * @param customer the customer class
   * @return a confirmation string
   */
  public static String addFromMenu(String choice, Customer customer) {
    switch (choice) {
      case "1":
        customer.addItem(new Item("Crunchy taco", "15", "70.00"));
        return "Added Crunchy taco to order";
      case "2":
        customer.addItem(new Item("Soft taco", "15", "74.00"));
        return "Added Soft taco to order";
      case "3":
        customer.addItem(new Item("Burrito", "8", "105.00"));
        return "Added Burrito to order";
      case "4":
        customer.addItem(new Item("Beef", "8, 6", "320.00"));
        return "Added Beef to order";
      case "5":
        customer.addItem(new Item("Vegetarian beef", "5", "310.00"));
        return "Added Vegetarian beef to order";
      case "6":
        customer.addItem(new Item("Tomatoes", "15", "10.00"));
        return "Added Tomatoes to order";
      case "7":
        customer.addItem(new Item("Cheese", "6", "45.00"));
        return "Added Cheese to order";
      case "8":
        customer.addItem(new Item("Bacon", "15", "76.00"));
        return "Added Bacon to order";
      case "9":
        customer.addItem(new Item("Nachos", "8, 6", "350.00"));
        return "Added Nachos to order";
      case "10":
        customer.addItem(new Item("Churros", "8, 6", "156.00"));
        return "Added Churros to order";
      default:
        return "Not a valid option";
    }
  }

  /**
   * main method
   * 
   * @param args none applicable
   */
  public static void main(String[] args) {

    ConnectToDatabase connect = new ConnectToDatabase();
    Connection connection = connect.connect();
    String choice = "";

    scanner = new Scanner(System.in);

    List<Order> orders = new ArrayList<>();
    Menu menu = new Menu();
    Customer customer = new Customer();
    Kitchen kitchen = new Kitchen(connection);
    // FilterMenu filter = new FilterMenu(connect);

    // String[] allergens = new String[15];

    System.out.println("Welcome to the Oaxaca Restaurant Management System");
    while (true) {
      menu.createMenu(connection);
      boolean quit = false;
      System.out.println(
          "Please select an option from below: \n" + "1: I am a customer\n" + "2: I am a waiter\n"
              + "3: I am a kitchen staff");

      int ans = scanner.nextInt();

      if (ans == 1) {
        while (!quit) {
          System.out.println("Please select from the menu: ");
          customer.update(connection);
          System.out.println(menu);
          // Honestly fixing this is beyond me
          // filter.filter();
          choice = scanner.next();
          System.out.println(addFromMenu(choice, customer));
          System.out.println("Select a option:\n1: Continue\n2: Confirm order\n3: Review order\n4: cancel order");
          choice = scanner.next();
          switch (choice) {
            case "2":
              System.out.println(customer.createOrder(connection));
              quit = true;
              break;
            case "3":
              System.out.println(customer.showOrder());
              break;
            case "4":
              System.out.println(customer.clearOrder());
              break;
            default:
              // just do nothing
          }
        }
      } else if (ans == 2) {
        while (!quit) {
          System.out.println(
              "Select an option:\n" + "1: Add an order to the list\n" + "2: Mark an order delivered\n"
                  + "3: See the list of orders\n" + "4: Cancel an order\n" + "Q: quit");
          choice = scanner.next();
          switch (choice) {
            case "1":
              System.out.println("filler");
              break;
            case "2":
              System.out.println("filler");
              break;
            case "3":
              System.out.println("filler");
              break;
            case "4":
              System.out.println("filler");
              break;
            default:
              quit = true;

          }
        }
      } else if (ans == 3) {
        while (!quit) {
          System.out.println("Select an option:\n" + "1: See all orders chronologically\n"
              + "2: confirm recent orders\n"
              + "3: Notify Waiter to get order\n"  + "Q: quit");
          choice = scanner.next();

          switch (choice) {
            case "1":
              Order order = new Order(Status.IN_PROGRESS, 12, 123, null, 1);
              orders.add(order);
              order = new Order(Status.IN_PROGRESS, 11, 124, null, 2);
              orders.add(order);
              order = new Order(Status.IN_PROGRESS, 10, 125, null, 3);
              orders.add(order);
              NotifyWaiter.getOrderTime(orders);
              break;
            case "2":
              System.out.println(kitchen.confirmOrder());
              break;
            case "3":
              order = new Order(Status.IN_PROGRESS, 10, 123, null, 1);
              orders.add(order);
              order = new Order(Status.IN_PROGRESS, 12, 124, null, 2);
              orders.add(order);
              order = new Order(Status.IN_PROGRESS, 12, 125, null, 3);
              orders.add(order);
              NotifyWaiter.ReadyOrder(orders);
              break;
            default:
              quit = true;
          }
        }
      } else {
        quit();
      }
    }
  }
}
