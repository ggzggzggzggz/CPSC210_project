package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

import static ui.ListDemo.createAndShowGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
