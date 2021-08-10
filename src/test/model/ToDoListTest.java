package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class ToDoListTest {

    private ToDoList test;

    @BeforeEach
    public void setup() {
        test = new ToDoList();
    }

    @Test
    public void addTaskTest() {
        test.addTask("test1");
        assertTrue(test.isInList("test1"));
        assertFalse(test.isInList("test2"));
        test.addTask("test2");
        assertTrue(test.isInList("test2"));
    }

    @Test
    public void addTaskByTaskTest() {
        Task t1 = new Task("test1");
        Task t2 = new Task("test2");
        test.addTaskByTask(t1);
        assertTrue(test.isInList("test1"));
        assertFalse(test.isInList("test2"));
        test.addTaskByTask(t2);
        assertTrue(test.isInList("test2"));
    }

    @Test
    public void deleteTaskTest() {
        test.addTask("test1");
        test.addTask("test2");
        assertTrue(test.isInList("test1"));
        assertTrue(test.isInList("test2"));
        try {
            test.deleteTask("test1");
        } catch (Exception e) {
           fail("Not found");
        }
        try {
            test.deleteTask("test3");
            fail("No exception was thrown");
        } catch (Exception e) {
        }
        assertFalse(test.isInList("test1"));
        assertTrue(test.isInList("test2"));
    }

    @Test
    public void getSizeTest() {
        assertEquals(0, test.getSize());
        test.addTask("test1");
        assertEquals(1, test.getSize());
        test.addTask("test2");
        assertEquals(2, test.getSize());
    }

    @Test
    public void isInListTest() {
        test.addTask("test1");
        assertTrue(test.isInList("test1"));
        assertFalse(test.isInList("test2"));
        test.addTask("test2");
        assertTrue(test.isInList("test2"));

    }

    @Test
    public void changeStatusTest() {
        test.addTask("test1");
        test.addTask("test2");
        assertFalse(test.getStatus("test1"));
        test.changeStatus(true,"test1");
        assertTrue(test.getStatus("test1"));
        test.changeStatus(false,"test1");
        assertFalse(test.getStatus("test1"));
    }

    @Test
    public void showStatusTest() {
        test.addTask("test1");
        assertEquals("Name: test1     Status: NOT DONE",test.showStatus(0));
        test.addTask("test2");
        assertEquals("Name: test2     Status: NOT DONE",test.showStatus(1));
        test.changeStatus(true, "test1");
        assertEquals("Name: test1     Status: DONE",test.showStatus(0));
    }

    @Test
    public void doneNumberTest() {
        test.addTask("test1");
        test.addTask("test2");
        assertEquals(0,test.doneNumber());
        test.changeStatus(true,"test1");
        assertEquals(1,test.doneNumber());
        test.changeStatus(true,"test2");
        assertEquals(2,test.doneNumber());
    }

    @Test
    public void notDoneNumberTest() {
        test.addTask("test1");
        test.addTask("test2");
        assertEquals(2,test.notDoneNumber());
        test.changeStatus(true,"test1");
        assertEquals(1,test.notDoneNumber());
        test.changeStatus(true,"test2");
        assertEquals(0,test.notDoneNumber());
    }

    @Test
    public void getTest() {
        test.addTask("test1");
        test.addTask("test2");
        assertTrue(test.get(0).equals(new Task("test1")));
        assertTrue(test.get(1).equals(new Task("test2")));
    }
}

