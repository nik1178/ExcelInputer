import java.awt.event.*;
import java.util.*;

import javax.swing.*;
public class ComboExcel<E> extends JComboBox<E> {

    ArrayList<E> values = new ArrayList<>();

    ComboExcel() {
        super();
        this.addActionListener(this);
    }

    void addValue(E name, E amount, E price) {
        this.addItem(name);
        this.addItem(amount);
        this.addItem(price);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getSelectedItem());
    }
}