package com.example.project;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * Allows the customer to call help from a waiter
 *
 */
class CallWaiter {
    boolean waiterReq = false;
    WaiterRec acknowledged;

    /**
     * 
     * @return waiterReq, a boolean that will be made true if the button
     *         to request a waiter is pressed
     */
    public boolean requestWaiter() {
        // Scanner is a placeholder for button.
        Scanner obj = new Scanner(System.in);
        System.out.println("Is the waiter button pressed?\nYes: 1\nNo: 2");
        String press = obj.nextLine();
        if (press.equals("1")) {
            waiterReq = true;
        }
        return waiterReq;
    }

    /**
     * 
     * @return alert, a String that sends the appropriate message to the waiters
     *         depending on the status of the boolean waiterReq
     */
    public String sendToWaiter() {
        if (waiterReq == true) {
            String alert = "A customer needs help!";
            return alert;
        }
        String alert = "False alarm.";
        return alert;
    }

    public void confirm(WaiterRec acknowledged) {
        if (acknowledged.recieveReq()) {
            System.out.println("A member of staff is on the way!");
        }
    }
}
