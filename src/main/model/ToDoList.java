package model;

import java.util.ArrayList;

public class ToDoList {
    ArrayList<Task> toDoList = new ArrayList<>();

    //EFFECTS: create a new todolist
    public ToDoList(){
    }

    //MODIFIES: this
    //EFFECTS: add a task in todolist
    public void addTask(Task task) {
        toDoList.add(task);
    }

    //REQUIRES: this task has been in todolist
    //MODIFIES: this
    //EFFECTS: delete a task in todolist
    public void deleteTask(Task task) {
        toDoList.remove(task);
    }

    //EFFECTS: return the number of task which has not been done
    public int notDoneTaskNumber() {
        int i = 0;
        for (int a = toDoList.size() - 1; a >= 0; a--) {
            if (!toDoList.get(a).getStatus()) {
                i++;
            }
        }
        return i;
    }

    //EFFECTS: check whether a task in the list
    public boolean isInList(Task task) {
        return toDoList.contains(task);
    }

    //EFFECTS: get the size of list
    public int getSize() {
        return toDoList.size();
    }

    //EFFECTS: change task status
    public void changeStatus(boolean b, Task task) {
        task.setStatus(b);
    }
}
