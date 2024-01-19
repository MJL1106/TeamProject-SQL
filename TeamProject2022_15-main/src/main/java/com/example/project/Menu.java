package com.example.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This Menu Class is used to populate our menu from a file.
 * 
 * @author Matthew Lowe, Piotr Kulczak
 *
 */
public class Menu {
  List<Item> menu = new ArrayList<Item>();

  /**
   * method to put allergen ids to appropiate names
   * 
   * @param number the number of the allergen id
   * @return the allergen's name
   */
  private String idToName(String number) {
    switch (number) {
      case "1":
        return "moluscs";
      case "2":
        return "eggs";
      case "3":
        return "fish";
      case "4":
        return "lupin";
      case "5":
        return "soya";
      case "6":
        return "milk";
      case "7":
        return "peanuts";
      case "8":
        return "gluten";
      case "9":
        return "crustacenas";
      case "10":
        return "mustard";
      case "11":
        return "nuts";
      case "12":
        return "sesame";
      case "13":
        return "celery";
      case "14":
        return "sulphites";
      case "15":
        return "none";
      default:
        return "none";
    }
  }

  /**
   * Creates a menu that will be accessible with getMenu()
   * 
   * @param connection An active sql connection
   */
  public void createMenu(Connection connection) {
    menu.clear();
    // Search menu object and make an item from it:
    try (Statement stmt = connection.createStatement();) {
      String sql = String.format("SELECT * FROM menu ORDER BY item_id");
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String price, name, type, allergens = "";
        int calories;
        Boolean in_stock;
        String[] allergen;
        price = rs.getString(3);
        name = rs.getString(2);
        allergen = (String[]) rs.getArray(4).getArray();
        calories = rs.getInt(8);
        type = rs.getString(9);
        in_stock = rs.getBoolean(5);
        for (int x = 0; x < allergen.length; x++) {
          allergens += idToName(allergen[x]) + ", ";
        }
        allergens = allergens.substring(0, allergens.length() - 2);
        menu.add(new Item(name, allergens, price, calories, type, in_stock));
      }
      rs.close();
    } catch (SQLException e) {
      e.getMessage();
    }
  }

  /**
   * Function to change the stock of an item on the menu
   * 
   * @param connection an active PSQL connection
   * @param id The item_id of the menu item
   */
  public void changeStock(Connection connection, int id) {
    menu.clear();
    try (Statement stmt = connection.createStatement();) {
      String sql = String.format("SELECT in_stock FROM menu WHERE item_id = %d", id);
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      Boolean stock = rs.getBoolean(1);
      rs.close();
      if (stock) {
        sql = String.format("UPDATE menu SET in_stock='f' WHERE item_id = %d", id);
        stmt.executeUpdate(sql);
      } else {
        sql = String.format("UPDATE menu SET in_stock='t' WHERE item_id = %d", id);
        stmt.executeUpdate(sql);
      }
    } catch (SQLException e) {
      e.getMessage();
    }
  }

  /**
   * Used to obtain the menu list
   * 
   * @return the menu list
   */
  public List<Item> getMenu() {
    return menu;
  }

  /**
   * Prints the current menu to the terminal
   */
  public void printMenu() {
    for (int i = 0; i < menu.size(); i++) {
      System.out.println(menu.get(i).toString());
    }
  }

}
