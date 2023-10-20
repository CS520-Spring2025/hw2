package filter;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AmountFilter implements  TransactionFilter{

    private Double amount;

    public AmountFilter(String amount){
      double amountDouble = Double.parseDouble(amount);
      this.amount = amountDouble;
    }
    public List<Transaction> filter(List<Transaction> inputList){
        List<Transaction> filteredList = new ArrayList<>();
        for(Transaction transaction : inputList)
        {
            if(transaction.getCategory().equals(this.amount))
            {
                filteredList.add(transaction);
            }
        }
        return filteredList;
    }

}
