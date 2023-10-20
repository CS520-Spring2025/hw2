package filter;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilter implements TransactionFilter{

    private String category;

    public CategoryFilter(String category) {
      this.category = category;
    }

    public List<Integer> filter(List<Transaction> inputList){
        List<Integer> filteredList = new ArrayList<>();
        for(int i= 0; i< inputList.size();i++)
        {
            if(inputList.get(i).getCategory().equals(this.category))
            {
                filteredList.add(i);
            }
        }
      return filteredList;
    }
}
