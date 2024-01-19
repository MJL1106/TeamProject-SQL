package com.example.project;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.json.*;
import java.util.*;

/**
 * This program implements the connection to the PostgreSQL database used for
 * the menu.
 * 
 * @author phoebe200801
 */
class ConnectToDatabase {

    public Connection conn;
    // Just saving memory
    private String status;
    private String sql;
    private Order order;
    ArrayList<Order> orders = new ArrayList<Order>();

    /**
     * Constructor for ConnectToDatabase
     */
    public void connectToDatabase() {
        this.connect();
    }

    /**
     * This function creates the connection to the menu database by taking in the
     * three parameters and using them to login to access the menu information.
     *
     * @return Connection This returns the connection to the database.
     */
    public Connection connect() {
        String url = "jdbc:postgresql://ec2-54-195-141-170.eu-west-1.compute.amazonaws.com:5432/ddfdi9gm4ef56u";
        String user = "whobryvjjlncsi";
        String password = "69b40a67a269c4cbe2761ff2dc65cd5c4f7dd22b546bc126ad22e4956ce24d98";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Method to empty a table
     * 
     * @param conn      Active SQL connection
     * @param tableName Name of the table to be cleared
     * @return The error or a confirmation string
     */
    public String clearTable(Connection conn, String tableName) {
        try (Statement stmt = conn.createStatement();) {
            sql = "DELETE FROM " + tableName + " WHERE true;";
            // If the table exists this should always execute even if it deletes nothing
            stmt.executeUpdate(sql);
            return "cleared table " + sql;
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Method used primarily by the customer class to add an order to the database
     * in the orders table
     * 
     * @param conn  An active SQL connection
     * @param order The order as represented by a list of items
     * @param ID    The customer ID
     * @return A confirmation message or an error
     */
    public String addOrder(Connection conn, List<Item> order, int ID) {
        try (Statement stmt = conn.createStatement();) {
            // Need to start order details like this so the array is within another array
            String orderDetails = "'{";
            for (int x = 0; x < order.size(); x++) {
                if (x + 1 == order.size()) {
                    // adding just the name to the details for now as the table is only id, and
                    // items
                    orderDetails += "{ " + order.get(x).getName() + " }}'";
                } else {
                    // if the order has multiple items add them appropiately to the array with a
                    // comma
                    orderDetails += "{ " + order.get(x).getName() + " },";
                }
            }
            java.util.Date date = new java.util.Date();
            sql = String.format("INSERT INTO orders VALUES (%d, %s, 'unpaid', '%s');", ID, orderDetails,
                    date.toString());
            stmt.executeUpdate(sql);
            return String.format("Created unpaid order number %s with details: \n%s", ID, orderDetails);
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }

    }

    /**
     * Gets the max value within a column of a SQL table
     * 
     * @param conn       An active SQL connection
     * @param columnName Name of the column to recieve the max value from
     * @param tableName  Name of the SQL table
     * @return The max value or 0 if there's an error
     */
    public int getMax(Connection conn, String columnName, String tableName) {
        int result = 0;
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("SELECT MAX(%S) FROM %S", columnName, tableName);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                // As it's max the first result should be the highest value
                result = rs.getInt(1);
                ;
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            // If there's an error the table likely doesn't exist so return 0
            return result;
        }
    }

    /**
     * Shows the result of 2 columns in a table
     * 
     * @param conn Active SQL connection
     * @return An error message or the full table
     */
    public ArrayList<Order> getTableOrder(Connection conn) {
        // Garbage collector should deallocate memory for the objects
        orders.clear();
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("SELECT * FROM orders ORDER BY cust_id");
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                order = new Order();
                order.setOrderNum(rs.getInt(1));
                order.setItemStrings(rs.getString(2));
                order.setStatus(Status.valueOf(rs.getString(3).toUpperCase()));
                order.setTimeString(rs.getString(4));
                orders.add(order);
            }
            rs.close();
            return orders;
        } catch (SQLException e) {
            // results.add(e.getMessage());
            return orders;
        }
    }

    /**
     * Shows a specific order's status
     * 
     * @param conn Active SQL connection
     * @param id   Customer ID
     * @return An error message or status
     */
    public String getOrderStatus(Connection conn, int id) {
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("SELECT * FROM orders WHERE cust_id = " + String.valueOf(id));
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            status = (rs.getString(3).toUpperCase());
            rs.close();
            return status;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    /**
     * Changes the status in the database
     * 
     * @param conn   An active SQL connection
     * @param id     The id of the database entry
     * @param status The status to change the entry to
     * @return A confirmation string
     */
    public String changeOrderStatus(Connection conn, int id, Status status) {
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("UPDATE orders SET status ='%s' WHERE cust_id=%d", status.toString(), id);
            stmt.executeUpdate(sql);
            return String.format("updated order #%d to status %s", id, status);
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    /**
     * Get the number of tables in the resturant as per the tables sql table
     * 
     * @param conn an active sql connection
     * @return the table size, or -1 if there's an error
     */
    public int tableSize(Connection conn){
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tables");
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            return count;
        } catch (SQLException e) {
            e.getMessage();
            return -1;
        }
    }

    /**
     * Gets the status of the tables as in the SQL table
     * 
     * @param conn An active SQL connection
     * @return a Linked list of every table's status, or an empty one if there's an error
     */
    public LinkedList<String> tableStatus(Connection conn){
        LinkedList<String> list = new LinkedList<>();
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables ORDER BY table_num");
            while(rs.next()) {
                rs.getString(2);
                if (rs.wasNull()){
                    list.add(null);
                } else {
                    list.add(rs.getString(2));
                }
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            e.getMessage();
            return list;
        }
    }

    public void setTableStatus(Connection conn, int tableNumber, int orderNumber){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("UPDATE tables SET status='%d' WHERE table_num=%d",orderNumber,tableNumber);
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public LinkedList<String> getHelpStatus(Connection conn){
        LinkedList<String> list = new LinkedList<>();
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables ORDER BY table_num");
            while(rs.next()) {
                rs.getString(3);
                if (rs.wasNull()){
                    list.add("no");
                } else {
                    list.add("yes");
                }
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            e.getMessage();
            return list;
        }
    }

    public void resetHelpStatus(Connection conn, int tableNumber){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("UPDATE tables SET help=null WHERE table_num=%d", tableNumber);
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void resetTable(Connection conn, int tableNumber){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("DELETE FROM tables WHERE table_num=%d;INSERT INTO tables VALUES (%d, null, null, null);", tableNumber, tableNumber);
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void changeHelpStatus(Connection conn, int tableNumber){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("SELECT * FROM tables WHERE table_num=%d", tableNumber);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                rs.getString(3);
                if (rs.wasNull()){
                    sql = String.format("UPDATE tables SET help='true' WHERE table_num=%d", tableNumber);
                    stmt.executeQuery(sql);
                } else {
                    sql = String.format("UPDATE tables SET help=null WHERE table_num=%d", tableNumber);
                    stmt.executeQuery(sql);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void changeTablePaid(Connection conn, int tableNumber){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("SELECT paid FROM tables WHERE table_num=%d", tableNumber);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                rs.getString(1);
                if (rs.wasNull()){
                    sql = String.format("UPDATE tables SET paid='yes' WHERE table_num=%d", tableNumber);
                    stmt.executeQuery(sql);
                } else {
                    sql = String.format("UPDATE tables SET paid=null WHERE table_num=%d", tableNumber);
                    stmt.executeQuery(sql);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //Method here to check the order number's status based off the tables table, figured this was a good point to stop
    public LinkedList<String> getTablePaid(Connection conn){
        LinkedList<String> list = new LinkedList<>();
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables ORDER BY table_num");
            while(rs.next()) {
                rs.getString(4);
                if (!(rs.wasNull())){
                    list.add(rs.getString(4));
                } else {
                    list.add("no");
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return list;
    }

    public void changeTableDetails(Connection conn, int tableNum, String status){
        try (Statement stmt = conn.createStatement();) {
            sql = String.format("UPDATE tables SET status='%s' WHERE table_num=%d", status, tableNum);
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * A method to make a JSON file from the menu table
     * 
     * @param conn A SQL connection
     * @return a populated JsonArray for the menu or a JsonArray that says empty if
     *         a failure occurs
     */
    public static JsonArray createMenuJson(Connection conn) {
        JsonArray menu = Json.createArrayBuilder().add("empty").build();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM menu";
            ResultSet rs = stmt.executeQuery(sql);

            // Creating a JSON array
            JsonArrayBuilder array = Json.createArrayBuilder();

            while (rs.next()) {
                // Inserting key-value pairs to JSON and making an object
                JsonObject item = Json.createObjectBuilder()
                        .add("ID", rs.getInt("item_id"))
                        .add("Data",
                                Json.createObjectBuilder()
                                        .add("name", rs.getString("selection"))
                                        .add("price", rs.getDouble("price"))
                                        .add("allergen_ids", rs.getString("allergen_ids"))
                                        .add("in_stock", rs.getBoolean("in_stock"))
                                        .add("calories", rs.getString("calories"))
                                        .add("type", rs.getString("type")))
                        .build();
                array.add(item);
            }
            menu = array.build();

            // Writing the JSON object to a file
            FileWriter file = new FileWriter("src/main/resources/static/menu.json");
            file.write(menu.toString());
            file.close();
        } catch (SQLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return menu;
    }
}
