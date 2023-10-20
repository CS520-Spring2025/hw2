package filter;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AmountFilter implements TransactionFilter{

    private Double amount;

    public AmountFilter(double amount){
      this.amount = amount;
    }
    public List<Integer> filter(List<Transaction> inputList){
        List<Integer> filteredList = new ArrayList<>();
        for(int i= 0; i< inputList.size();i++)
        {
            if(inputList.get(i).getAmount()== this.amount)
            {
                filteredList.add(i);
            }
        }
        return filteredList;
    }

}
