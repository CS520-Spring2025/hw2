package controller;

import view.ExpenseTrackerView;

import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  private TransactionFilter amtFilter;
  private  TransactionFilter catFilter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.catFilter = new CategoryFilter();
    this.amtFilter = new AmountFilter();

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

  // Other controller methods
  public void applyFilter(List<Object> filterList, String filterType){
        List<Transaction> allTransactions = model.getTransactions();
        List<Transaction> transactions;
        if(filterType.equalsIgnoreCase("category")){
          transactions = catFilter.filter(allTransactions, filterList);
        }
        else{
          transactions = amtFilter.filter(allTransactions, filterList);
        }
        view.refreshTable(transactions);
  }
}