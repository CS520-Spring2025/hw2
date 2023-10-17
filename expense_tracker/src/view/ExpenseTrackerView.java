package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.InputValidation;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;
import model.filters.AmountFilter;
import model.filters.CategoryFilter;
import controller.ExpenseTrackerController;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JTextField filterInput;
  private JComboBox<String> filterComboBox;
  private ExpenseTrackerController controller;
  private List<Integer> filteredRows = new ArrayList<>();
  private List<Transaction> filteredTransactions = new ArrayList<>();


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
    JButton applyFilterBtn = new JButton("Apply Filter");
    applyFilterBtn.addActionListener(e -> {
      filteredRows.clear();
      String filterType = (String) filterComboBox.getSelectedItem();
      if ("Amount".equals(filterType)) {
        try {
          double amount = Double.parseDouble(filterInput.getText());
          filteredTransactions = controller.applyFilter(new AmountFilter(amount));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, "Invalid amount format!");
          return;
        }
      } else if ("Category".equals(filterType)) {
        filteredTransactions = controller.applyFilter(new CategoryFilter(filterInput.getText()));
      } else {
        return;
      }

      List<Transaction> allTransactions = controller.getAllTransactions();
      for (int i = 0; i < allTransactions.size(); i++) {
        if (filteredTransactions.contains(allTransactions.get(i))) {
          filteredRows.add(i);
        }
      }
      transactionsTable.repaint();
      refreshTable(controller.getAllTransactions());
    });


    // Create table
    transactionsTable = new JTable(model);
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
          c.setBackground(table.getSelectionBackground());
        } else {
          if (filteredRows.contains(row)) {
            c.setBackground(new Color(173, 255, 168)); // Light green
          } else {
            c.setBackground(table.getBackground());
          }
        }
        return c;
      }
    });


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
    setSize(400, 300);
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
}
