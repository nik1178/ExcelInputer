import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class MainFrame extends JFrame implements EventListener{
    Main mainClass;

    public ComboExcel<Row> comboBox = new ComboExcel<>();
    ArrayList<InputField> inputFields = new ArrayList<>();
    AddButton addButton;
    SaveButton saveButton;

    Font font = new Font("Arial", Font.PLAIN, 20);
    MainFrame(Main mainClass) {
        super();
        this.mainClass = mainClass;
        setupFrame();
        this.setVisible(true);
    }

    void setupFrame() {
        // Set JFrame properties
        this.setTitle("Podatki");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Add 3 input fields
        this.inputFields.add(new InputField("Name", this));
        this.inputFields.add(new InputField("Amount", this));
        this.inputFields.add(new InputField("Price", this));

        // Add 2 button
        this.addButton = new AddButton("Add", this);
        this.saveButton = new SaveButton("Save", this);

        this.add(comboBox);
        for (InputField inputField : this.inputFields) {
            this.add(inputField);
        }
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(this.saveButton);
        controlPanel.add(this.addButton);
        this.add(controlPanel);

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
        this.saveButton.setFont(this.font);
    }

    public void addValue(String name, String amount, String price) {
        // Add value to combo box
        Row row = mainClass.sheet.createRow(mainClass.sheet.getLastRowNum()+(this.mainClass.emptyFile ? 0 : 1));
        Cell cell = row.createCell(0);
        cell.setCellValue(name);
        cell = row.createCell(1);
        cell.setCellValue(amount);
        cell = row.createCell(2);
        cell.setCellValue(price);
        this.comboBox.addValue(name, row);
        saveExcelFile();
    }

    public boolean saveExcelFile() {
        this.mainClass.emptyFile=false;
        try {
            FileOutputStream out = new FileOutputStream(mainClass.podatkiFile);
            mainClass.workbook.write(out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
