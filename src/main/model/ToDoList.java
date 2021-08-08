package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;

// Represents a todolist having tasks
public class ToDoList implements Writable {
    ArrayList<Task> toDoList = new ArrayList<>();

    //EFFECTS: create a new todolist
    public ToDoList(){
    }

    //MODIFIES: this
    //EFFECTS: add a task in todolist
    public void addTask(String s) {
        Task task = new Task(s);
        toDoList.add(task);
    }

    //MODIFIES: this
    //EFFECTS: add a task in todolist by input task
    public void addTaskByTask(Task t) {
        toDoList.add(t);
    }

    //MODIFIES: this
    //EFFECTS: delete a task in todolist
    public void deleteTask(String s) {
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName().equals(s)) {
                toDoList.remove(toDoList.get(a));
            }
        }
    }

    //EFFECTS: return whether a task in the list
    public boolean isInList(String s) {
        boolean b = false;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName().equals(s)) {
                b = true;
            }
        }
        return b;
    }

    //EFFECTS: change task status
    public void changeStatus(boolean b, String s) {
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName().equals(s)) {
                toDoList.get(a).setStatus(b);
            }
        }
    }

    //REQUIRES: this task has been in todolist
    //EFFECTS: return task status
    public boolean getStatus(String s) {
        boolean b = false;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName().equals(s)) {
                b = toDoList.get(a).getStatus();
            }
        }
        return b;
    }

    //EFFECTS: return the size of todolist
    public int getSize() {
        return toDoList.size();
    }

    //REQUIRES: 0 <= int i <= list size -1
    //EFFECTS: return a task name and status
    public String showStatus(int i) {
        String s;
        if (toDoList.get(i).getStatus()) {
            s = "Name: " + toDoList.get(i).getName() + "     Status: DONE";
        } else {
            s = "Name: " + toDoList.get(i).getName() + "     Status: NOT DONE";
        }
        return s;
    }

    //EFFECTS: return the number of done tasks
    public int doneNumber() {
        int i = 0;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getStatus()) {
                i++;
            }
        }
        return i;
    }

    //EFFECTS: return the number of not done tasks
    public int notDoneNumber() {
        int i = 0;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (!toDoList.get(a).getStatus()) {
                i++;
            }
        }
        return i;
    }

    // EFFECTS: returns task by giving index number
    public Task get(int i) {
        return toDoList.get(i);
    }

    public void removeIndex(int i) {
        toDoList.remove(i);
    }

    public DefaultListModel getDefaultListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Task val : toDoList) {
            if (val.getStatus()) {
                model.addElement(val.getName() + "      DONE");
            } else {
                model.addElement(val.getName() + "  NOT DONE");
            }

        }
        return model;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", toDoListToJson());
        return json;
    }

    // EFFECTS: returns tasks in this toDoList as a JSON array
    private JSONArray toDoListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Task task : toDoList) {
            jsonArray.put(task.toJson());
        }
        return jsonArray;
    }
}
