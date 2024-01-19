package com.example.project;

import java.io.*;
import java.sql.*;
import java.util.*;

class WaiterRec {
  private boolean waiterReq;
  private boolean acknowledged = false;

  /**
   * Constructor for class
   *
   * @param waiterReq
   * @param acknowledged
   */
  public WaiterRec(boolean waiterReq, boolean acknowledged) {
    this.waiterReq = waiterReq;
    this.acknowledged = acknowledged;
  }

  /**
   * Called when customer requests for help and sends message to Waiters.
   */
  public boolean recieveReq() {
      System.out.println("A customer needs your help!");
      return true;
  }

  /**
   * Asks the Waiters if they are taking action to solve the
   * customers' issue
   *
   * @param acknowledged
   * @param waiterReq
   */
  public void requestAck(boolean acknowledged, CallWaiter waiterReq) {
    //Scanner placeholder for pop up
    Scanner obj = new Scanner(System.in);
    System.out.println("Are you going to solve the issue?\n1: Yes\n2: No");
    String ack = obj.nextLine();
    if (ack == "1") {
      this.waiterReq = false;
      this.acknowledged = true;
    } else {
      this.acknowledged = false;
    }
  }
}
