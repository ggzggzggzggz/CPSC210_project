package model;

// Represents a task having name and status
public class Task {
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
}
