package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// Task application
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
    private ToDoList td;

    // EFFECTS: runs the task application
    public ListDemo() {
        super(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel();
        listModel.addElement("test       DONE");
        initializeList();
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: remove whatever selected.
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            deletePlay();

            int size = listModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
                doneButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add task
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = taskName.getText() + "   NOT DONE";
            if (name.equals("") || alreadyInList(taskName.getText() + "   NOT DONE")
                    || alreadyInList(taskName.getText() + "       DONE")
                    || alreadyInList(taskName.getText())) {
                Toolkit.getDefaultToolkit().beep();
                taskName.requestFocusInWindow();
                taskName.selectAll();
                return;
            }
            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            listModel.insertElementAt(taskName.getText() + "   NOT DONE", index);
            addPlay();
            taskName.requestFocusInWindow();
            taskName.setText("");
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // EFFECTS: check whether in list
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // MODIFIES: this
        // EFFECTS: enable button
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // MODIFIES: this
        // EFFECTS: unable button
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // MODIFIES: this
        // EFFECTS: change button status
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: enable button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: unable button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // EFFECTS: play add.wav
    private void addPlay() {
        String gongFile = "./src/main/ui/audio/add.wav";
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
    }

    // EFFECTS: play delete.wav
    private void deletePlay() {
        String gongFile = "./src/main/ui/audio/delete.wav";
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
    }

    // EFFECTS: play done.wav
    private void donePlay() {
        String gongFile = "./src/main/ui/audio/done.wav";
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
    }

    // EFFECTS: play other.wav
    private void otherPlay() {
        String gongFile = "./src/main/ui/audio/other.wav";
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
    }

    // MODIFIES: this
    // EFFECTS: change the value selected
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
                doneButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
                doneButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: create the GUI and show it
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new ListDemo();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize list
    public void initializeList() {
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

    // MODIFIES: this
    // EFFECTS: initialize load list
    private void loadList() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // MODIFIES: this
    // EFFECTS: initialize save list
    private void saveList() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this
    // EFFECTS: initialize done list
    private void doneList() {
        doneButton = new JButton(doneString);
        doneButton.setActionCommand(doneString);
        doneButton.addActionListener(new DoneListener());
    }

    // MODIFIES: this
    // EFFECTS: initialize delete list
    private void deleteList() {
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
    }

    // MODIFIES: this
    // EFFECTS: initialize add list
    private AddListener addList() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        return addListener;
    }

    // MODIFIES: this
    // EFFECTS: initialize button
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

    // MODIFIES: this
    // EFFECTS: make whatever selected to be done
    private class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            donePlay();
            int index = list.getSelectedIndex();
            String s = String.valueOf(listModel.get(index));
            s = s.substring(0,s.length() - 10);
            s = s + "      DONE";
            listModel.remove(index);
            listModel.addElement(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: save todolist
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            otherPlay();
            int i = listModel.getSize() - 1;
            td = new ToDoList();
            while (i >= 0) {
                String status = String.valueOf(listModel.get(i));
                status = status.substring(status.length() - 6);
                String s = String.valueOf(listModel.get(i)).substring(0,String.valueOf(listModel.get(i)).length() - 10);
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

    // MODIFIES: this
    // EFFECTS: load todolist
    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            otherPlay();
            td = new ToDoList();
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
