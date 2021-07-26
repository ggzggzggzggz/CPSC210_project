package model;

import java.util.ArrayList;

// Represents a todolist having tasks
public class ToDoList {
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
    //EFFECTS: delete a task in todolist
    public void deleteTask(String s) {
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName() == s) {
                toDoList.remove(toDoList.get(a));
            }
        }
    }

    //EFFECTS: return whether a task in the list
    public boolean isInList(String s) {
        boolean b = false;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName() == s) {
                b = true;
            }
        }
        return b;
    }

    //EFFECTS: change task status
    public void changeStatus(boolean b, String s) {
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName() == s) {
                toDoList.get(a).setStatus(b);
            }
        }
    }

    //REQUIRES: this task has been in todolist
    //EFFECTS: return task status
    public boolean getStatus(String s) {
        boolean b = false;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (toDoList.get(a).getName() == s) {
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
}
