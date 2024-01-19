package com.example.project;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests
 */
class CustomerTest {

    // Items used for testing
    Item potato = new Item("potato", "gluten?", 200 );
    Item magic = new Item("magic", "", 2);
    Customer customer = new Customer();

    // Checks Item allergies function
    @Test
    void testCheckItemAllegries() {
        assertEquals("No Allergen", customer.checkItemAllegries(magic));
        assertEquals("Allergen(s) :gluten?", customer.checkItemAllegries(potato));
    }

    // Checks Order calories function
    @Test
    void testCheckOrderCalories() {
        assertEquals("no items", customer.checkOrderCalories());
        customer.addItem(potato);
        assertEquals("Total calories is: 200", customer.checkOrderCalories());
        customer.addItem(magic);
        assertEquals("Total calories is: 202", customer.checkOrderCalories());
    }

    // Test add item function
    @Test
    void testAddItem() {
        assertEquals("Added potato to order",customer.addItem(potato));
    }

}
