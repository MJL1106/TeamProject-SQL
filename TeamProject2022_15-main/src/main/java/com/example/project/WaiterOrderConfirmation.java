package com.example.project;

import java.io.*;
import java.util.*;
/**
 * Allows the order to be set confirmed.
 *
 */
public class WaiterOrderConfirmation {

  public boolean isOrder;
  public Random rand;
  public boolean confirm;
  public int orderNum;

  public WaiterOrderConfirmation() {
    this.isOrder = false;
    this.rand = new Random();
  }

  // NOTE: order number will have to be called from the customer class

  // Recieve order number from customer
  // Add orderNum to stack
  public boolean confirmOrderNum(int orderNum, boolean confirm) {
    if (isOrder = true) {
      return confirm = true;
    }
    return confirm = false;
  }

  /**
   * Send confirmed queue to kitchen.
   *
   * @param orders a queue that will now have the order number added to it.
   * @return
   */
  public OrderQueue sendToKit(OrderQueue orders, int orderNum) {
    if (confirm == true) {
      orders.enqueue(orderNum);
      return orders;
    }
    return null;
  }
}


