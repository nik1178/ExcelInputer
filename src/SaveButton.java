import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// Literally the save button
public class SaveButton extends AddButton {
    SaveButton(String text, MainFrame frame) {
        super(text, frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<String> values = readInputFields();
        if (values == null) {
            return;
        }
        
        this.frame.addValue(values, this.frame.comboBox.getSelectedIndex());
    }
}