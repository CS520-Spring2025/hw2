package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.InputValidation;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JButton filterBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private JTextField filterField;
  private DefaultTableModel model;
  private JComboBox<String> categoryDropdown;

  private List<Integer> selectedRows = new ArrayList<>();

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    filterBtn = new JButton("Filter");
    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();
    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);


    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    filterField = new JTextField(10);

    categoryDropdown = new JComboBox<>(new String[]{"Category", "Amount"});


    // Create table
    transactionsTable = new JTable(model) {

      public TableCellRenderer getCellRenderer(int row, int column) {
        return new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the row is in the selectedRows list
            if (selectedRows.contains(row)) {
              c.setBackground(new Color(173, 255, 168)); // Light green
            } else {
              c.setBackground(table.getBackground());
            }
            return c;
          }
        };
      }
    };


    // Layout components
    JPanel inputPanel = new JPanel(new GridLayout(1, 12));
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    inputPanel.add(filterBtn); //ask doubt
    // Add the category dropdown to the input panel
//    inputPanel.add(categoryDropdown);
//    inputPanel.add(filterField);

    //my code
    JPanel addTransactionPanel = new JPanel();
    addTransactionPanel.add(addTransactionBtn);
    JPanel filterPanel = new JPanel();
    filterPanel.add(filterBtn);
    JPanel filterFieldPanel = new JPanel();
    filterFieldPanel.add(filterField);
    JPanel categoryDropdownPanel = new JPanel();
    categoryDropdownPanel.add(categoryDropdown);


    // Create the buttonPanel with a GridLayout
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4)); // Two buttons in a row
    buttonPanel.add(addTransactionPanel);
    buttonPanel.add(categoryDropdownPanel);
    buttonPanel.add(filterFieldPanel);
    buttonPanel.add(filterPanel);

    //end of my code

    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
    // Add the filterPanel to the frame
//    add(filterPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  public JButton getFilterBtn() {
    return filterBtn;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public String getFilterField() {
    return filterField.getText();
  }

  public String getDropDownOption(){
    return (String)categoryDropdown.getSelectedItem();
  }
  public static boolean isNumeric(String str) {
    try {
      int number = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public void setSelectedRows(List<Integer> colouredRows)
  {
    selectedRows = colouredRows;
  }
}
