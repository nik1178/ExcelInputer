import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class MainFrame extends JFrame implements EventListener{
    XSSFSheet sheet;

    public ComboExcel<String> comboBox;
    ArrayList<InputField> inputFields = new ArrayList<>();
    AddButton addButton;

    Font font = new Font("Arial", Font.PLAIN, 20);
    MainFrame(XSSFSheet sheet) {
        this.sheet = sheet;
        setupFrame();

        readExcelData();

        this.setVisible(true);
    }

    void setupFrame() {
        // Set JFrame properties
        this.setTitle("Podatki");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Add 1 combo box
        this.comboBox = new ComboExcel<String>();

        // Add 3 input fields
        this.inputFields.add(new InputField("Name", this));
        this.inputFields.add(new InputField("Amount", this));
        this.inputFields.add(new InputField("Price", this));

        // Add 1 button
        this.addButton = new AddButton("Add", this);

        this.add(comboBox);
        for (InputField inputField : this.inputFields) {
            this.add(inputField);
        }
        this.add(this.addButton);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                adjustTextSize();
            }
        });

        this.pack();
    }

    public void adjustTextSize() {
        //Make text adjust to window size
        int newFontSize = (int) (this.getWidth() * 0.05);
        this.font = this.font.deriveFont((float) newFontSize);

        this.comboBox.setFont(this.font);
        for (InputField inputField : this.inputFields) {
            inputField.setFont(this.font);
        }
        this.addButton.setFont(this.font);
    }

    public void readExcelData() {
        Iterator<Row> rowIterator = this.sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            String name = "";
            String amount = "";
            String price = "";

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getColumnIndex()) {
                    case 0:
                        name = cell.getStringCellValue();
                        break;
                    case 1:
                        amount = cell.getStringCellValue();
                        break;
                    case 2:
                        price = cell.getStringCellValue();
                        break;
                }
            }
            this.comboBox.addValue(name, amount, price);
        }
    }
}
