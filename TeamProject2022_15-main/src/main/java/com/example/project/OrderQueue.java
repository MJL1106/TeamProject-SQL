package com.example.project;
/**
 * This class initialises a queue and uses the enqueue() and dequeue() functions
 * to grow and shrink a queue as items are added/removed.
 */
public class OrderQueue {

    int[] orders;
    int front;
    int back;

    public OrderQueue() {
      this.orders = new int[1000];
      this.front = 0;
      this.back = 0;
    }

    /**
     * @param i An integer variable used to track the size of the queue.
     */
    public void enqueue(int i) {
      this.orders[back] = i;
      this.back++;
    }

    /**
     * @return the integer variable i which holds the content of orders[front].
     */
    public int dequeue() {
      int i = this.orders[front];
      this.front++;
      return i;
    }

}
