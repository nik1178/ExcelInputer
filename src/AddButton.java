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
        String name = readName();
        if (name.length() == 0) return;

        String amount = readAmount();
        if (amount.length() == 0) return;

        String price = readPrice();
        if (price.length() == 0) return;
        
        ComboExcel<String> combo = this.frame.comboBox;
        combo.addValue(name, amount, price);
    }

    public String readName() {
        String name = "0";
        if (name.length() == 0) {
            System.out.println("Name is empty.");
            return "";
        }
        return name;
    }

    public String readAmount() {
        String amount = "0";
        if (amount.length() == 0) {
            System.out.println("Amount is empty.");
            return "";
        }
        return amount;
    }

    public String readPrice() {
        String price = "0";
        if (price.length() == 0) {
            System.out.println("Price is empty.");
            return "";
        }
        return price;
    }
}
