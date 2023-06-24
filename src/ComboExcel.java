import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

// Combo box with ArrayList<E> values
public class ComboExcel<E> extends JComboBox<String> {
    MainFrame frame;

    ArrayList<E> values = new ArrayList<>();
    ComboExcel(MainFrame frame) {
        super();
        this.frame = frame;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getSelectedItem());
        System.out.println(this.getSelectedIndex());
        this.frame.updateInputFields(this.getSelectedIndex());
    }

    public E getValues(int index) {
        return this.values.get(index); 
    }

    public void addValue(String display, E value) {
        this.values.add(value);
        this.addItem(display);
    }

    public void editValue(String display, E value, int index) {
        this.values.set(index, value);
        this.insertItemAt(display, index);
        this.removeItemAt(index+1);
    }
}