package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task test;

    @BeforeEach
    public void setup() {
        test = new Task("1");
    }

    @Test
    public void getNameTest() {
        assertEquals("1", test.getName());
    }

    @Test
    public void getAndSetStatusTest() {
        assertFalse(test.getStatus());
        test.setStatus(true);
        assertTrue(test.getStatus());
    }
}