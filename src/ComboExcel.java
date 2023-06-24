import java.awt.event.*;
import java.util.*;

import javax.swing.*;
public class ComboExcel<E> extends JComboBox<String> {

    ArrayList<E> values = new ArrayList<>();
    ComboExcel() {
        super();
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getSelectedItem());
        
    }

    public void addValue(String display, E value) {
        this.values.add(value);
        this.addItem(display);
    }
}