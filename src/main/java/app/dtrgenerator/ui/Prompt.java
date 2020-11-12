package app.dtrgenerator.ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import app.dtrgenerator.constant.Default;

public class Prompt {
    private static final String ERROR = "Error";
    private static final String INFORMATION = "Information";
    private static JLabel label;

    public static void displayError(String message) {
        label = new JLabel(message);
        label.setFont(Default.FONT);
        JOptionPane.showMessageDialog(null, label, ERROR, JOptionPane.ERROR_MESSAGE);
    }

    public static void displayInformation(String message) {
        label = new JLabel(message);
        label.setFont(Default.FONT);
        JOptionPane.showMessageDialog(null, label, INFORMATION, JOptionPane.INFORMATION_MESSAGE);
    }
}