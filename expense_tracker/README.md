# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## Updates1
ExpenseTrackerModel Class
- The transactions list has been encapsulated to ensure data security. Direct modification from outside the class is no longer possible.
- The getter method for transactions now returns an unmodifiable version of the list to ensure data immutability.

Transaction Class
- All fields are now private and final, ensuring data immutability.
- Setter methods have been removed, and a transaction object's state cannot be modified after its creation.


## Updates2
Expense Tracker Update: Filtering Functionality
New Features:
1.TransactionFilter Interface: Standardizes transaction filtering with List<Transaction> filter(List<Transaction>) method.
2.Filter Types:
-CategoryFilter: Filter transactions by category.
-AmountFilter: Filter by specified amount.
3.Controller Updates: Added applyFilter() in ExpenseTrackerController. Filters transactions and updates UI.
4.View Updates: UI enhancements in ExpenseTrackerView to select filter type ("Amount" or "Category"), input filter criteria, and apply filter. Filtered transactions are now highlighted in the table.

Usage:
Select filter type from dropdown.
Input filter value.
Click 'Apply Filter' to view and highlight filtered transactions.






