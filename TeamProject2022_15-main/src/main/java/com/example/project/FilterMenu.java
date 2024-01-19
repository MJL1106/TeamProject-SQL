package com.example.project;

import java.sql.*;
import java.util.*;

/**
 * Class to filter a menu
 */
class FilterMenu {

  private ConnectToDatabase connect;
  private Connection conn;

  public FilterMenu(ConnectToDatabase connect){
    this.connect = connect;
    this.conn = this.connect.connect();
  }

  /**
   * requests inputs from customers to filter the menu.
   */
  public void filter() {
    boolean quit = false;

    String foodType;

    // output options menu for filtering
    // this will be changed when the UI is made
    while (!quit) {
      try (Scanner input = new Scanner(System.in)) {
          System.out.println("Enter dietary conditions:");
          System.out.println("1: No Dietry Conditions\n2: Vegan\n3: Dairy Free\n4: Gluten Free\nq: Quit");
          foodType = input.nextLine();
          switch (foodType) {
            case ("1"):
              fillChoice(conn);
              break;
            case ("2"):
              lactoseInt(conn);
              break;
            case ("3"):
              vegan(conn);
              break;
            case ("4"):
              glutenFree(conn);
              break;
            case ("q"):
              quit = true;
              break;
            default:
              System.out.println("Invalid choice");
              break;
          }
        System.exit(1);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @param conn  This is a variable of type Connection used to connect to the database.
   * @param query This is the string used to query the database.
   */
  public static ResultSet executeQuery(Connection conn, String query) {
    System.out.println("EXECUTING QUERY...");
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      return rs;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Requests customer's choice of filling, and drops all foods not in their
   * chosen option.
   */
  public static void fillChoice(Connection conn) throws SQLException {
    System.out.println("Enter what filling you would like:");
    try (Scanner input = new Scanner(System.in)) {
      System.out.println("1: Chicken\n2: Beef\n3: Vegetarian");
      int filling = input.nextInt();
      if (filling == 1) {
        // Remove all food items not including "Chicken".
        String fillingQuery = "SELECT * FROM Menu WHERE CONTAINS(Fillings, 'Chicken');";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(fillingQuery)) {
          while (rs.next()) {
            System.out.println(rs.getString(1));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } else if (filling == 2) {
        // Remove all food items not including "Beef".
        String fillingQuery = "SELECT * FROM Menu WHERE CONTAINS(Fillings, 'Beef');";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(fillingQuery)) {
          while (rs.next()) {
            System.out.println(rs.getString(1));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } else if (filling == 3) {
        // Remove all food items including "Beef" or "Chicken".
        String fillingQuery =
          "SELECT * FROM Menu WHERE NOT CONTAINS(Fillings, 'Chicken' AND 'Beef');";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(fillingQuery)) {
          while (rs.next()) {
            System.out.println(rs.getString(1));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } else {
        System.out.println("Invalid input, please enter a number from 1-3.");
      }
    }
  }

  /**
   * Asks whether customer is lactose intolerant, if they are, all options
   * including dairy are removed.
   */
  public static void lactoseInt(Connection conn) throws SQLException {
    System.out.println("Enter what filling you would like:");
    try (Scanner input = new Scanner(System.in)) {
      System.out.println("1: Chicken\n2: Beef\n3: Vegetarian");
      int lact = input.nextInt();
      if (lact == 1) {
        // Remove all dairy options from the list
        String lactoseQuery = "SELECT * FROM Menu WHERE NOT CONTAINS(Allergies, 'Dairy');";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(lactoseQuery)) {
          while (rs.next()) {
            System.out.println(rs.getString(1));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } else if (lact == 2) {
        // Return the whole list
        String lactoseQuery = "SELECT * FROM Menu;";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(lactoseQuery)) {
          while (rs.next()) {
            System.out.println(rs.getString(1));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } else {
        System.out.println("Invalid input, please enter a number from 1-3.");
      }
    }
  }

  /**
   * @param conn This is a variable of type Connection used to connect to the database.
   * @throws SQLException This is thrown if the query has a mistake in.
   *                      This function prints the menu items which are vegan (do not contain meat (beef or chicken), dairy or fish).
   */
  public static void vegan(Connection conn) throws SQLException {
    String veganQuery =
      "SELECT * FROM Menu WHERE NOT CONTAINS(Fillings, 'Chicken' AND 'Beef') AND NOT CONTAINS(Allergens, 'dairy' AND 'fish' AND 'crustaceans' AND 'moluscs');";

    try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(veganQuery)) {
      while (rs.next()) {
        System.out.println(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @param conn This is a variable of type Connection used to connect to the database.
   * @throws SQLException This is thrown if the query has a mistake in.
   *                      This function prints the menu items which do NOT contain gluten.
   */
  public static void glutenFree(Connection conn) throws SQLException {
    String glutenFreeQuery = "SELECT * FROM Menu WHERE NOT CONTAINS(Allergens, 'gluten');";

    try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(glutenFreeQuery)) {
      while (rs.next()) {
        System.out.println(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
