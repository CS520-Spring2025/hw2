package filter;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilter implements TransactionFilter{

    private String category;

    public CategoryFilter(String category) {
      this.category = category;
    }

    public List<Transaction> filter(List<Transaction> inputList){
        List<Transaction> filteredList = new ArrayList<>();
        for(Transaction transaction : inputList)
        {
            if(transaction.getCategory().equals(this.category))
            {
                filteredList.add(transaction);
            }
        }
      return filteredList;
    }
}
