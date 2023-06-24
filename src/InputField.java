import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// JPanel that has both a JLabel and JTextField, so that it is easier to organize
public class InputField extends JPanel{
    String text;
    JLabel label;
    JTextField textField;
    MainFrame frame;
    InputField(String text, MainFrame frame) {
        super();
        this.text = text;
        this.frame = frame;

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
}
