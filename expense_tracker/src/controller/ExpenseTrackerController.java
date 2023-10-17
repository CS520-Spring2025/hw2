package controller;

import view.ExpenseTrackerView;
import model.Transaction;
import model.filters.TransactionFilter;
import model.ExpenseTrackerModel;

import java.util.ArrayList;
import java.util.List;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private List<Transaction> transactions;

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


  public List<Transaction> applyFilter(TransactionFilter filter) {
    List<Transaction> transactions = model.getTransactions();

    List<Transaction> filteredTransactions = filter.filter(transactions);

    view.refreshTable(filteredTransactions);
    return filteredTransactions;
  }
  // Other controller methods
}