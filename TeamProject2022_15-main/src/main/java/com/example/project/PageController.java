package com.example.project;

import java.sql.Connection;

import javax.json.JsonArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller for webpages that are served up with Spring
 */
@Controller public class PageController {

  static ConnectToDatabase connect = new ConnectToDatabase();
  static Connection connection = connect.connect();
  Menu menu = new Menu();
  Customer customer = new Customer();
  Kitchen kitchen = new Kitchen(connection);
  static JsonArray menuArray = ConnectToDatabase.createMenuJson(connection);

  /**
   * The default greeting webpage from the spring tutorial, it's only here as a
   * reference
   *
   * @param name  the name used in the get request if it's used, by default the
   *              value is world
   * @param model A representation of a java object
   * @return The greeting webpage
   */
  @GetMapping("/greeting") public String greeting(
    @RequestParam(name = "name", required = false, defaultValue = "World") String name,
    Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  /**
   * The webpage used by the kitchen staff
   *
   * @param model a model representing a java object
   * @return The kitchen webpage
   */
  @RequestMapping("/kitchen") public String kitchen(Model model) {
    model.addAttribute("orders", kitchen.getOrders());
    return "kitchen";
  }

  /**
   * A webpage that stores the result of order confirmation
   *
   * @param model  a model representing a java object
   * @param status the status of the order
   * @param id     the order id
   * @return the confirmation webpage
   */
  @RequestMapping("/kitchen_confirmation") public String kitchenConfirm(
    @RequestParam(name = "status", required = false, defaultValue = "UNPAID") Status status,
    @RequestParam(name = "id", required = false, defaultValue = "-1") String id, Model model) {
    if (id.equals("-1")) {
      model.addAttribute("text", kitchen.confirmOrder());
    } else {
      model.addAttribute("text", kitchen.changeOrderStatus(Integer.valueOf(id) + 1, status));
    }
    return "kitchen_confirmation";
  }

  /**
   * The webpage used by the waiting staff
   *
   * @return The waiter webpage
   */
  @RequestMapping("/waiter") public String waiter() {
    return "waiter";
  }

  /**
   * The page waiters can view their tables on
   *
   * @return The tables page
   */
  @RequestMapping("/tables") public String tables(
    @RequestParam(name = "clear", required = false, defaultValue = "-1") String clear,
    @RequestParam(name = "paid", required = false, defaultValue = "-1") String paid,  
    Model model) {
  if(!(clear.equals("-1"))){
    customer.resetTable(connection, Integer.valueOf(clear)+1);
  }
  if(!(paid.equals("-1"))){
    customer.changeTablePaid(connection, Integer.valueOf(paid)+1);
  }
	model.addAttribute("tables", customer.tableNumber(connection));
	model.addAttribute("tableStatus", customer.tableStatus(connection));
  model.addAttribute("helpStatus", customer.getHelpStatus(connection));
  model.addAttribute("paid", customer.getTablePaid(connection));
    return "tables";
  }

  /**
   * The page waiters can view their orders on
   *
   * @return The orders page
   */
  @RequestMapping("/waiter_orders") public String waiterOrders(Model model) {
    model.addAttribute("orders", kitchen.getOrders());
    return "waiter_orders";
  }

  @RequestMapping("/waiter_orders_cancel") public String waiterCancel(
    @RequestParam(name = "id", required = false, defaultValue = "-1") String id,  
	@RequestParam(name = "stock", required = false, defaultValue = "-1") String stock, Model model){
    model.addAttribute("text", kitchen.changeOrderStatus(Integer.valueOf(id) + 1, Status.CANCELLED));
	if (Integer.valueOf(stock) != -1){
		menu.changeStock(connection, Integer.valueOf(stock)+1);
	}
    return "waiter_orders_cancel";
  }

  /**
   * The page waiters can view the menu on
   *
   * @return The waiter menu page
   */
  @RequestMapping("/waiter_menu") public String waiterMenu(Model model) {
    menu.createMenu(connection);
    model.addAttribute("menu", menu.getMenu());
    return "waiter_menu";
  }

  /**
   * The webpage used by a customer
   *
   * @param model A model representing a java object, used to send the menu to the
   *              webpage
   * @return The customer webpage
   */
  @RequestMapping("/customer") public String customer2(
    @RequestParam(name = "table", required = false, defaultValue = "-1") String table, 
    @RequestParam(name = "help", required = false, defaultValue = "0") String help,
    Model model) {
    // Move the create menu somewhere else in future if the menu doesn't get changed
    // in future
    menu.createMenu(connection);
	if (!(table.equals("-1"))){
		customer.changeTable(connection, Integer.valueOf(table), "in use");
    if (help.equals("0")){
      customer.resetHelpStatus(connection, Integer.valueOf(table));
    } else {
      customer.changeTableHelp(connection, Integer.valueOf(table));
    }
	}
    model.addAttribute("menu", menu.getMenu());
    return "customer2";
  }

  @RequestMapping("/select") public String select_table(Model model){
	model.addAttribute("tables", customer.tableNumber(connection));
	model.addAttribute("tableStatus", customer.tableStatus(connection));
	return "select_table";
  }

  /**
   * The page the customer is taken to once they have made their payment to track their order
   *
   * @return The order tracking page
   */
  @RequestMapping("/customer_order_tracking") public String customer_order_tracking(
    @RequestParam(name = "id", required = false, defaultValue = "-1") Integer id, 
    @RequestParam(name = "table", required = false, defaultValue = "-1") Integer table,
    @RequestParam(name = "help", required = false, defaultValue = "-1") Integer help,
    Model model) {
    if (help==1){
      customer.changeTableHelp(connection, table);
    }
    model.addAttribute("orderStatus", kitchen.getOrderStatus(id));
    model.addAttribute("id", id);
    return "customer_order_tracking";
  }

  /**
   * This page is sent customer requests to respond in realtime with the order status
   *
   * @return The customer's order status
   */
  @RequestMapping("/confirm_order") public String confirm_order(
    @RequestParam(name = "id", required = false, defaultValue = "-1") Integer id, Model model) {
    model.addAttribute("text", kitchen.getOrderStatus(id));
    return "confirm_order";
  }

  /**
   * The webpage used by a customer when confirming their payment methods
   *
   * @param model a model representing a java object
   * @param order the customer order
   * @return The payment page
   */
  @RequestMapping("/payment") public String payment(
    @RequestParam(name = "order", required = false, defaultValue = "Order confirmation here")String order, 
    @RequestParam(name = "table", required = false, defaultValue = "-1") String table,
    Model model) {
    customer.update(connection);
    customer.clearOrder();
    if (order.equals("Order confirmation here")) {

    } else {
      String[] array = order.split(",");
      String result = "";
      for (String value : array) {
        addFromMenu(value, customer);
      }
      result = customer.createOrder(connection);
      order = result;

    }
    if (!(table.equals("-1"))){
      customer.setTableStatus(connection, Integer.valueOf(table), customer.getCustomerID());
    }
    int ID = customer.getCustomerID();
    model.addAttribute("order", order);
    model.addAttribute("id", ID);
    return "payment";
  }

  /**
   * The default page
   *
   * @return The default page when loaded
   */
  @RequestMapping("/") public String index() {
    return "index";
  }

  /**
   * adding things from menu to the customer
   *
   * @param choice   the menu option entered
   * @param customer the customer class
   * @return a confirmation string
   */
  public static String addFromMenu(String choice, Customer customer) {
    customer.addItem(new Item(
      menuArray.getJsonObject(Integer.valueOf(choice)).getJsonObject("Data").getString("name"),
      menuArray.getJsonObject(Integer.valueOf(choice)).getJsonObject("Data")
        .getString("allergen_ids"), Integer.toString(
      menuArray.getJsonObject(Integer.valueOf(choice)).getJsonObject("Data").getInt("price"))));
    return "Added" + menuArray.getJsonObject(Integer.valueOf(choice)).getJsonObject("Data")
      .getString("name") + "to order";
  }
}
