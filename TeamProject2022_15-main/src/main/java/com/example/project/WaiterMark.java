package com.example.project;

import java.io.*;
import java.util.*;

public class WaiterMark {

  boolean delivered;
  OrderQueue currOrders;
  OrderQueue delQueue;
  int orderNum;

  public WaiterMark() {
    currOrders = new OrderQueue();
    delQueue = new OrderQueue();
  }

  /**
   * Asks the user whether the order has been delievered,
   * returns true or false depending on the input.
   *
   * @return true if input is 1, otherwise false.
   */

  public boolean checkDel() {
    Scanner check = new Scanner(System.in);
    System.out.println("Has the order been delivered? 1: Yes 2: No");
    String isDel = check.nextLine();
    if (isDel.equals("1")) {
      check.close();
      return delivered = true;
    } else if (isDel.equals("2")) {
      check.close();
      return delivered = false;
    } else {
      check.close();
      return delivered = false;
    }
  }

  /**
   * This method checks if an order has been delivered.
   * If it has, it removes the order from the queue of current orders and adds it to the queue of delivered orders.
   *
   * @param delivered A boolean variable taken in to check whether the order has been delivered or not.
   * @return The contents of delQueue (all delivered orders).
   */
  public OrderQueue addToDelivered(boolean delivered) {
    if (delivered = true) {
      // remove from order queue
      currOrders.dequeue();
      // add to delivered queue
      delQueue.enqueue(orderNum);
    }
    return delQueue;
  }
}
