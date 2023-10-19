package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import java.util.ArrayList;
import java.util.List;
import controller.ExpenseTrackerController;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JButton applyFilterBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JTextField filterInput;
  private JComboBox<String> filterComboBox;
  private ExpenseTrackerController controller;

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Filter components
    JLabel filterLabel = new JLabel("Filter by:");
    filterInput = new JTextField(10);
    filterComboBox = new JComboBox<>(new String[]{"Amount", "Category"});
    applyFilterBtn = new JButton("Apply Filter");

    // Create table
    transactionsTable = new JTable(model);

    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);

    JPanel buttonPanel = new JPanel(new BorderLayout());
    buttonPanel.add(addTransactionBtn, BorderLayout.WEST);

    JPanel filterPanel = new JPanel();
    filterPanel.add(filterLabel);
    filterPanel.add(filterComboBox);
    filterPanel.add(filterInput);
    filterPanel.add(applyFilterBtn);
    buttonPanel.add(filterPanel, BorderLayout.EAST);

    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // Set frame properties
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  // Calculate total cost and populate the table
  public void refreshTable(List<Transaction> transactions) {
    model.setRowCount(0);
    int rowNum = 0;
    double totalCost = 0;

    for (Transaction t : transactions) {
      totalCost += t.getAmount();
      model.addRow(new Object[]{++rowNum, t.getAmount(), t.getCategory(), t.getTimestamp()});
    }

    Object[] totalRow = {"Total", null, null, totalCost};
    model.addRow(totalRow);

    transactionsTable.updateUI();
  }

  public void setController(ExpenseTrackerController controller) {
    this.controller = controller;
  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
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

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  // ... rest of ExpenseTrackerView class ...

  public JButton getApplyFilterBtn() {
    return applyFilterBtn;
  }

  public String getSelectedFilterType() {
    return (String) filterComboBox.getSelectedItem();
  }

  public String getFilterValue() {
    return filterInput.getText();
  }
}