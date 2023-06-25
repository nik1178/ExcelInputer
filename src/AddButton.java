import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class AddButton extends JButton implements ActionListener {
    MainFrame frame;
    AddButton(String text, MainFrame frame) {
        super(text);
        this.frame = frame;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.showErrorMessage(" ");
        ArrayList<String> values = readInputFields();
        if (values == null) {
            return;
        }
        
        this.frame.addValue(values);
        this.frame.comboBox.setupSelectedIndex(this.frame.comboBox.getItemCount()-1);
    }

    public ArrayList<String> readInputFields() {
        ArrayList<String> values = new ArrayList<>();
        int counter = 0;
        for (InputField inputField : this.frame.inputFields) {
            String current = inputField.getInput();
            if (current == null || current.length() == 0) {
                System.out.println("Empty input field at index "+counter+"!");
                return null;
            }
            values.add(inputField.textField.getText());
            counter++;
        }
        return values;
    }
}