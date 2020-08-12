package app.dtrgenerator.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.dtrgenerator.constant.Constant;
import net.sourceforge.jdatepicker.JDatePicker;

public class Action implements ActionListener {
    public enum ActionType {
        OPEN, SAVE, EXPORT
    }

    private ActionType actionType;
    private Component component;

    public Action(ActionType actionType, Component component) {
        this.component = component;
        this.actionType = actionType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (actionType) {
            case OPEN:
                this.fileOpen();
                break;

            case SAVE:
                this.fileSave();
                break;

            case EXPORT:
                this.export();
                break;

            default:
                break;
        }
    }

    private void fileOpen() {
        JFileChooser fileChooser = new JFileChooser(Constant.DEFAULT_PATH);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(Constant.XLSX_FILTER);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JTextField textField = (JTextField) this.component;
            textField.setText(selectedFile.getPath());
        }
    }

    private void fileSave() {
        JFileChooser fileChooser = new JFileChooser(Constant.DEFAULT_PATH);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(Constant.XLSX_FILTER);
        fileChooser.setSelectedFile(new File(Constant.DEFAULT_FILE_NAME));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JTextField textField = (JTextField) this.component;
            textField.setText(selectedFile.getPath());
        }
    }

    private void export() {
        Window window = (Window) this.component;
        for (Component component : window.getPanel().getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                if (textField.getText().equals("")) {
                    window.displayMessage(Constant.ERROR_TITLE,
                            Constant.ERROR_MESSAGE + textField.getToolTipText() + ".", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (component instanceof JDatePicker) {
                JDatePicker datePicker = (JDatePicker) component;
                if (datePicker.getModel().getValue() == null) {
                    window.displayMessage(Constant.ERROR_TITLE, Constant.ERROR_MESSAGE + "'Cut-Off'.",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        window.export();
    }
}