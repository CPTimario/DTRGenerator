package app.dtrgenerator.ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import app.dtrgenerator.ui.Action.ActionType;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class ComponentBuilder {
    public enum ComponentType {
        LABEL, TEXT_FIELD, BUTTON, DATE_PICKER
    }

    private String text;
    private String tooltip;
    private Boolean enabled;
    private ActionType actionType;
    private Component component;

    public ComponentBuilder() {
        this.text = "";
        this.enabled = true;
    }

    public ComponentBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public ComponentBuilder setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public ComponentBuilder setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public ComponentBuilder setAction(ActionType actionType, Component component) {
        this.actionType = actionType;
        this.component = component;
        return this;
    }

    public Component build(ComponentType componentType) {
        switch (componentType) {
            case LABEL:
                JLabel label = new JLabel(this.text);
                label.setEnabled(this.enabled);
                return label;

            case TEXT_FIELD:
                JTextField textField = new JTextField();
                textField.setToolTipText(this.tooltip);
                textField.setEditable(this.enabled);
                return textField;

            case BUTTON:
                JButton button = new JButton(this.text);
                button.addActionListener(new Action(this.actionType, this.component));
                button.setEnabled(this.enabled);
                return button;

            case DATE_PICKER:
                UtilDateModel model = new UtilDateModel();
                JDatePanelImpl datePanel = new JDatePanelImpl(model);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
                datePicker.setEnabled(this.enabled);
                return datePicker;

            default:
                return null;
        }
    }
}