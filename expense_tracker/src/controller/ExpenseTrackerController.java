package controller;

import filter.AmountFilter;
import filter.CategoryFilter;
import filter.TransactionFilter;
import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private TransactionFilter transactionFilter;

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

  public List<Transaction> filterTransactions(String attribute , String attributeValue) {
    TransactionFilter filter;
    List<Transaction> selectedRows = new ArrayList<>();
    List<Integer> selectedRowsIndex = new ArrayList<>();
    if(attribute == "Category")
    {
      filter = new CategoryFilter(attributeValue);
    }
    else if(attribute == "Amount")
    {
      filter = new AmountFilter(attributeValue);
    }
    selectedRows = transactionFilter.filter(model.getTransactions());

    refresh();
    return selectedRows;
  }


  
  // Other controller methods
}