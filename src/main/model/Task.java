package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a task having name and status
public class Task implements Writable {
    private boolean status; //if done is true, have not done is false
    private String name; //name of the task

    //EFFECTS: set task name and set it need to be done
    public Task(String taskName) {
        name = taskName;
        status = false;
    }

    //EFFECTS: get task's name
    public String getName() {
        return name;
    }

    //EFFECTS: get task's status
    public boolean getStatus() {
        return status;
    }

    //MODIFIES: this
    //EFFECTS: change task's status to what I want
    public void setStatus(boolean b) {
        status = b;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("status", String.valueOf(status));
        return json;
    }

//    public boolean checkEqual(Task t) {
//        return t.getStatus() == status && t.getName() == name;
//    }
}
