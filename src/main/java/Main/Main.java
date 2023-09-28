package Main;

import UI.InitializeUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            InitializeUI initUI = new InitializeUI();
        } catch (Exception ignored) {}
    }
}
