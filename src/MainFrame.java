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

    public ComboExcel<ArrayList<String>> comboBox = new ComboExcel<>(this);
    ArrayList<InputField> inputFields = new ArrayList<>();
    AddButton addButton;
    SaveButton saveButton;
    JLabel errorLabel = new JLabel();

    Font font = new Font("Arial", Font.PLAIN, 20);
    MainFrame(Main mainClass) {
        super();
        this.mainClass = mainClass;
        setupFrame();
        readExcelData();
        this.setVisible(true);
    }

    // Setup the basic components in the JFrame
    void setupFrame() {
        // Set JFrame properties
        this.setTitle("Podatki");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Add 3 input fields
        this.inputFields.add(new InputField("Name", this));
        this.inputFields.add(new InputField("Amount", this));
        this.inputFields.add(new InputField("Price", this, InputFieldType.FLOAT));

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
        this.add(errorLabel); showErrorMessage(" ");

        // Detects when a component has been changed. Used for resizing the components
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                adjustTextSize();
                adjustInputFieldSize();
            }
        });

        this.pack();
    }

    // On program start, get all the data from the Excel File and save it in the comboBox.
    public void readExcelData() {
        Iterator<Row> rowIterator = this.mainClass.sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            addValueToCombo(rowToStringList(row));
        }
    }

    // When window is resized, components will change and calculate new font size based on window width. Currently based on nothing
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

    // When window is resized, components will change and calculate new input field size based on window width. Currently based on nothing
    public void adjustInputFieldSize() {
        //Make input fields adjust to window size
        int newInputFieldWidth = (int) (this.getWidth() * 0.6);
        for (InputField inputField : this.inputFields) {
            inputField.textField.setPreferredSize(new Dimension(newInputFieldWidth, newInputFieldWidth / 7));
        }
    }

    public void addValue(ArrayList<String> values) {
        if (values == null) {
            return;
        }
        addValueToCombo(values);
        saveValues(values, -1);
        saveExcelFile();
    }
    public void addValue(ArrayList<String> values, int index) {
        if (values == null) {
            return;
        }
        editValueInCombo(values, index);
        saveValues(values, index);
        saveExcelFile();
    }

    // Index -1 means add to the end of the file
    public void saveValues(ArrayList<String> values, int index) {
        if (index == -1) {
            index = mainClass.sheet.getLastRowNum()+(this.mainClass.emptyFile ? 0 : 1);
        }
        Row row = mainClass.sheet.createRow(index);
        for (int i = 0; i < values.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(values.get(i));
        }
    }

    // When add button is pressed, add the values from the input fields to the comboBox
    public void addValueToCombo(ArrayList<String> values) {
        // Add value to combo box
        this.comboBox.addValue(getDisplayName(values), values);
        saveExcelFile();
    }

    // When save button is pressed, save the values from the input fields to the comboBox in the currently selected combobox field
    public void editValueInCombo(ArrayList<String> values, int index) {
        this.comboBox.editValue(getDisplayName(values), values, index);
        saveExcelFile();
    }

    public String getDisplayName (ArrayList<String> values) {
        String displayName = "";
        for (String value : values) {
            displayName += value + " ";
        }
        return displayName;
    }

    // Whenever something is changed in the combobox, print the workboox to the excel file, so it has latest data
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

    // Converts a row Objec to an ArrayList<String> where each cell in the row is one value in the list
    public ArrayList<String> rowToStringList(Row row) {
        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                System.out.println("Cell "+i+" is null.");
                continue;
            }

            String displayValue = "";
            switch (cell.getCellType()) {  
                case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
                    displayValue = cell.getStringCellValue();
                    break;  
                case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
                    displayValue = String.valueOf(cell.getNumericCellValue());
                    break;  
                default:  
                    System.out.println("Invalid input in cell "+i+".");
            }  
            values.add(displayValue);
        } 

        return values;
    }

    // When a value is selected in the combobox, fill the input fields with the values from the selected item
    public void updateInputFields(int index) {
        if (index == -1) {
            return;
        }
        ArrayList<String> values = this.comboBox.getValues(index);
        for (int i = 0; i < values.size(); i++) {
            this.inputFields.get(i).textField.setText(values.get(i));
        }
    }

    public void showErrorMessage(String message) {
        this.errorLabel.setText(message);
    }
}
