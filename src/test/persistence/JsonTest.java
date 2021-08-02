package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, Boolean status, Task t) {
        assertEquals(name, t.getName());
        assertEquals(status, t.getStatus());
    }
}