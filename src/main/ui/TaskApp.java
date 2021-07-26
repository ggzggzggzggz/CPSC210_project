package ui;

import model.ToDoList;

import java.util.Scanner;

// Task application
public class TaskApp {
    private Scanner input;
    private ToDoList toDoList;

    // EFFECTS: runs the task application
    public TaskApp() {
        runTask();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTask() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("f")) {
            doFinish();
        } else if (command.equals("s")) {
            doShow();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes todolist
    private void init() {
        toDoList = new ToDoList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add task");
        System.out.println("\td -> delete task");
        System.out.println("\tf -> finish task");
        System.out.println("\ts -> show all tasks");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add a task to todolist
    private void doAdd() {
        System.out.print("Enter the task name:");
        String name = input.next();
        if (toDoList.isInList(name)) {
            System.out.printf("Sorry you have added " + name);
        } else {
            toDoList.addTask(name);
            System.out.printf("Successfully add " + name + " to task list");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete a task to todolist
    private void doDelete() {
        System.out.print("Enter the task name:");
        String name = input.next();
        System.out.println(name);
        if (!toDoList.isInList(name)) {
            System.out.printf("Sorry you have not added " + name);
        } else {
            toDoList.deleteTask(name);
            System.out.printf("Successfully delete " + name + " to task list");
        }
    }

    // MODIFIES: this
    // EFFECTS: change a task to finish
    private void doFinish() {
        System.out.print("Enter the task name:");
        String name = input.next();
        if (!toDoList.isInList(name)) {
            System.out.printf("Sorry you have not added " + name);
        } else if (toDoList.getStatus(name)) {
            System.out.printf("Successfully set " + name + " to a finished task");
        } else {
            toDoList.changeStatus(true, name);
            System.out.printf("Successfully set " + name + " to a finished task");
        }
    }

    // EFFECTS: show all tasks
    private void doShow() {
        for (int a = toDoList.getSize() - 1; a >= 0; a--) {
            System.out.printf(toDoList.showStatus(a) + "\n");
        }
    }
}
