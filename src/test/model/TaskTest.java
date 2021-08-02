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

    @Test
    public void equalsTest() {
        Task test1 = new Task("test");
        test1.setStatus(true);
        Task test2 = new Task("test");
        Task test3 = new Task("testtest");
        test1.setStatus(true);
        Task test4 = new Task("testtest");;
        assertFalse(test1.equals(test2));
        assertFalse(test1.equals(test3));
        assertFalse(test1.equals(test4));
        assertTrue(test1.equals(test1));
    }
}