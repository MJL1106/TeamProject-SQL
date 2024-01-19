package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Connection;
import org.junit.jupiter.api.Test;

/**
 * @author Matthew Lowe & Piotr Kulczak
 * <p>
 * JUnit test to test the functionality of Menu class.
 */
class TestMenu {

    Connection connection;
    Menu menu;

    /**
     * Tests that the display methods display the correct information.
     */
    @Test
    void testMenu() {
        menu = new Menu();
        ConnectToDatabase connect = new ConnectToDatabase();
        connection = connect.connect();
        menu.createMenu(connection);
        assertEquals(new Item("Crunchy taco", "none", "1.05", 70, "shell", true), menu.getMenu().get(0));
    }

}
