package ui;

import model.Task;
import model.ToDoList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ListDemo extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private static final String doneString = "Done";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private JButton deleteButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton doneButton;
    private JButton addButton;
    private JTextField taskName;
    private JScrollPane listScrollPane;
    private JPanel buttonPane;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";
    private DefaultListModel listModel;

    public ListDemo() {
        super(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel();
        listModel.addElement("test       DONE");
        initializeList();
        initializeButton();
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);
                doneButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = taskName.getText() + "   NOT DONE";

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(taskName.getText() + "   NOT DONE")
                    || alreadyInList(taskName.getText() + "       DONE")
                    || alreadyInList(taskName.getText())) {
                Toolkit.getDefaultToolkit().beep();
                taskName.requestFocusInWindow();
                taskName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(taskName.getText() + "   NOT DONE", index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            taskName.requestFocusInWindow();
            taskName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
                deleteButton.setEnabled(false);
                doneButton.setEnabled(false);

            } else {
            //Selection, enable the fire button.
                deleteButton.setEnabled(true);
                doneButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void initializeList() {
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        listScrollPane = new JScrollPane(list);

        AddListener addListener = addList();
        deleteList();
        doneList();
        saveList();
        loadList();

        taskName = new JTextField(10);
        taskName.addActionListener(addListener);
        taskName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();
    }

    private void loadList() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    private void saveList() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    private void doneList() {
        doneButton = new JButton(doneString);
        doneButton.setActionCommand(doneString);
        doneButton.addActionListener(new DoneListener());
    }

    private void deleteList() {
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
    }

    private AddListener addList() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        return addListener;
    }

    public void initializeButton() {
        //Create a panel that uses BoxLayout.
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(doneButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(taskName);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String s = String.valueOf(listModel.get(index));
            s = s.substring(0,s.length() - 10);
            s = s + "      DONE";
            listModel.remove(index);
            listModel.addElement(s);
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = listModel.getSize() - 1;
            ToDoList td = new ToDoList();
            while (i >= 0) {
                String status = String.valueOf(listModel.get(i));
                status = status.substring(status.length() - 6);
                String s = String.valueOf(listModel.get(i));
                s = s.substring(0,s.length() - 10);
                if (status.equals("T DONE")) {
                    td.addTask(s);
                } else {
                    Task t = new Task(s);
                    t.setStatus(true);
                    td.addTaskByTask(t);
                }
                i--;
            }
            try {
                jsonWriter.open();
                jsonWriter.write(td);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ToDoList td = new ToDoList();
            try {
                td = jsonReader.read();
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            int i = td.getSize() - 1;
            listModel.clear();
            while (i >= 0) {
                String s = td.get(i).getName();
                if (td.get(i).getStatus()) {
                    s = s + "      DONE";
                } else {
                    s = s + "  NOT DONE";
                }
                listModel.addElement(s);
                i--;
            }
        }
    }
}
