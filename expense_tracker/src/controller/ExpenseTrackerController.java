package controller;

import view.ExpenseTrackerView;
import model.Transaction;
import model.filters.TransactionFilter;
import model.filters.AmountFilter;
import model.filters.CategoryFilter;
import model.ExpenseTrackerModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private List<Transaction> transactions;
  private List<Transaction> filteredTransactions = new ArrayList<>();

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  public List<Transaction> getAllTransactions() {
    return model.getTransactions();
  }

  public List<Integer> applyFilterAndHighlight(String filterType, String filterValue) {
    List<Transaction> allTransactions = model.getTransactions();
    TransactionFilter transactionFilter = null;

    if ("Amount".equals(filterType)) {
      if(InputValidation.isValidAmount(Double.parseDouble(filterValue))) {
        double amount = Double.parseDouble(filterValue);
        transactionFilter = new AmountFilter(amount);
      } else {
        JOptionPane.showMessageDialog(null, "Invalid amount!");
        return new ArrayList<>();
      }
    } else if ("Category".equals(filterType)) {
      if (InputValidation.isValidCategory(filterValue)) {
        transactionFilter = new CategoryFilter(filterValue);
      } else {
        JOptionPane.showMessageDialog(null, "Invalid category!");
        return new ArrayList<>();
      }
    }

    if (transactionFilter != null) {
      filteredTransactions = transactionFilter.filter(allTransactions);
    }

    List<Integer> filteredRows = new ArrayList<>();
    for (int i = 0; i < allTransactions.size(); i++) {
      if (filteredTransactions.contains(allTransactions.get(i))) {
        filteredRows.add(i);
      }
    }

    // Update the view based on the filtered transactions
    view.refreshTable(filteredTransactions);

    // / Call setTableHighlighting to apply the highlighting on the filtered rows
    setTableHighlighting(filteredRows);

    return filteredRows;
  }

  private void setTableHighlighting(List<Integer> filteredRows) {
    JTable transactionsTable = view.getTransactionsTable();
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
  }
  // Other controller methods
}