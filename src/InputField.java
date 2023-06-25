import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

enum InputFieldType {
    STRING,
    FLOAT
}

// JPanel that has both a JLabel and JTextField, so that it is easier to organize
public class InputField extends JPanel{
    String text;
    JLabel label;
    JTextField textField;
    MainFrame frame;
    InputFieldType type = InputFieldType.STRING;
    InputField(String text, MainFrame frame) {
        super();
        this.text = text;
        this.frame = frame;

        this.label = new JLabel(text+" ");

        this.textField = new JTextField(text);

        this.add(label);
        this.add(textField);
    }

    InputField(String text, MainFrame frame, InputFieldType type) {
        super();
        this.text = text;
        this.frame = frame;
        this.type = type;

        this.label = new JLabel(text+" ");

        this.textField = new JTextField(text);

        this.add(label);
        this.add(textField);
    }

    public void setFont(Font font) {
        if (this.label == null || this.textField == null) {
            return;
        }
        this.label.setFont(font);
        this.textField.setFont(font);
    }

    public String getInput() {
        String value = this.textField.getText();
        if (value == null) {
            this.frame.showErrorMessage("Invalid input for "+this.text+".");
            System.out.println("Invalid input for "+this.text+".");
            return null;
        }
        if (value.length()==0) {
            this.frame.showErrorMessage("Empty input for "+this.text+".");
            System.out.println("Invalid input for "+this.text+".");
            return null;
        }
        boolean valid = false;
        switch (type) {
            case STRING:
                valid = isValidInputString(value);
                break;
            case FLOAT:
                    value = value.replace('.', ',');
                    valid = isValidInputFloat(value);
                break;
            default:
                System.out.println("Invalid type for input field.");
        }
        if (!valid) {
            this.frame.showErrorMessage("Invalid input for "+this.text+".");
            System.out.println("Invalid input for "+this.text+".");
            return null;
        }
        return value;
    }

    public boolean isValidInputString(String input) {
        return true;
    }

    public boolean isValidInputFloat(String input) {
        int dotCount = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ',') {
                dotCount++;
                if (dotCount > 1) {
                    return false;
                }
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}