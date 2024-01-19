package com.example.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the Kitchen class that notifies the waiter when an order is ready.
 * 
 * @author Matthew Lowe, Piotr Kulczak
 *
 */
public class NotifyWaiter {
  /**
   * Method reads a list of orders in progress orders and chooses an order that is ready.
   * 
   * @param orders is the list of orders in progress
   */
  public static void ReadyOrder(List<Order> orders) {
      for (int i = 0; i < orders.size(); i++) {
        System.out.println(orders.get(i).getOrderNum());
      }
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter Ready Order Number: ");
      String strReadyON = scanner.nextLine(); 
      System.out.println("");
      try {
          int readyON = Integer.parseInt(strReadyON);
          boolean found = false;
          for (int i = 0; i < orders.size(); i++) {
              if (readyON == orders.get(i).getOrderNum() && orders.get(i).getStatus() == Status.IN_PROGRESS) {
                  orders.get(i).setStatus(Status.READY);
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

  /**
   * Method reads a list of orders in progress orders and shows which orders are not ready chronologically.
   * 
   * @param orders is the list of orders in progress
   */
  public static void getOrderTime(List<Order> orders) {
    List<Order> unordered = new ArrayList<>();
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getStatus() == Status.IN_PROGRESS){
        unordered.add(orders.get(i));
      }
    }
    List<Order> final_list = insertionSort(unordered);
    for (int i = 0; i < final_list.size(); i++) {
      System.out.print("Order Num: " + final_list.get(i).getOrderNum());
      System.out.println(" Order time: " + final_list.get(i).getTime());
    }
  }

  /**
   * Runs an insertion sort algorithm on the list of orders.
   * 
   * @param arr is the list of orders in progress
   * @return sorted list
   */
  public static List<Order> insertionSort(List<Order> arr){
    for (int i = 0; i < arr.size(); i++) {
      if (i != 0){
        for (int j=i; j > 0; j--) {
          if (arr.get(j).getTime() < arr.get(j-1).getTime()){
            Order temp = arr.get(j-1);
            arr.set(j-1, arr.get(j));
            arr.set(j, temp);
          } else {
            break;
          }
        }
      }
    }
    return arr;
  }
}
