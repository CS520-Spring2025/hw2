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

  List<Integer> emptySelectedList = new ArrayList<>();

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
    view.setSelectedRows(emptySelectedList);
    refresh();
    return true;
  }

  public boolean filterTransactions(String attribute , String attributeValue) {

    TransactionFilter filter = null;
    if(attribute == "Category")
    {
      if (!InputValidation.isValidCategory(attributeValue)) {
        return false;
      }
      filter = new CategoryFilter(attributeValue);
    }
    else if(attribute == "Amount")
    {
      double amountDouble = Double.parseDouble(attributeValue);
      if (!InputValidation.isValidAmount(amountDouble)) {
        return false;
      }
      filter = new AmountFilter(amountDouble);
    }
    List<Integer> selectedRows  = filter.filter(model.getTransactions());
    view.setSelectedRows(selectedRows);
    refresh();
    return true;
  }


  
  // Other controller methods
}