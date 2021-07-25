package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoListTest {

    private ToDoList test;
    private Task test1;
    private Task test2;

    @BeforeEach
    public void setup() {
        test = new ToDoList();
        test1 = new Task("1");
        test2 = new Task("2");
    }

    @Test
    public void addTaskTest() {
        test.addTask(test1);
        assertTrue(test.isInList(test1));
        assertFalse(test.isInList(test2));
        test.addTask(test2);
        assertTrue(test.isInList(test2));
    }

    @Test
    public void deleteTaskTest() {
        test.addTask(test1);
        test.addTask(test2);
        assertTrue(test.isInList(test1));
        assertTrue(test.isInList(test2));
        test.deleteTask(test1);
        assertFalse(test.isInList(test1));
        assertTrue(test.isInList(test2));
    }

    @Test
    public void getSizeTest() {
        assertEquals(0,test.getSize());
        test.addTask(test1);
        assertEquals(1,test.getSize());
        test.addTask(test2);
        assertEquals(2,test.getSize());
    }

    @Test
    public void notDoneTaskNumberTest() {
        assertEquals(0,test.notDoneTaskNumber());
        test.addTask(test1);
        assertEquals(1,test.notDoneTaskNumber());
        test.addTask(test2);
        assertEquals(2,test.notDoneTaskNumber());
        test.changeStatus(true,test1);
        assertEquals(1,test.notDoneTaskNumber());
    }
}
