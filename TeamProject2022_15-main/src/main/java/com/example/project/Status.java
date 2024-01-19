package com.example.project;

/**
 * Status an order can be in
 */
public enum Status {
  /**
   * Recieved
   */
  ORDER_RECEIVED,
  /**
   * Unpaid
   */
  UNPAID,
  /**
   * Cooked
   */
  COOKED,
  /**
   * Cancelled
   */
  CANCELLED,
  /**
   * In progress
   */
  IN_PROGRESS,
  /**
   * Ready
   */
  READY,
  /**
   * Delivered
   */
  DELIVERED;
}
