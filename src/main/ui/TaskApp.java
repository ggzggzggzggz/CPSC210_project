package ui;

import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

// Task application
public class TaskApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private Scanner input;
    private ToDoList toDoList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the task application
    public TaskApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
        } else if (command.equals("p")) {
            doShow();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
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
        System.out.println("\tp -> print all tasks");
        System.out.println("\ts -> save todo list to file");
        System.out.println("\tl -> load todo list from file");
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
        System.out.printf("The number of completed tasks: " + toDoList.doneNumber() + "\n");
        System.out.println("The number of uncompleted tasks: " + toDoList.notDoneNumber());
    }

    // EFFECTS: saves the todolist to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(toDoList);
            jsonWriter.close();
            System.out.println("Saved " + "todolist" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadToDoList() {
        try {
            toDoList = jsonReader.read();
            System.out.println("Loaded " + "todolist" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
