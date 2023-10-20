package filter;

import model.Transaction;

import java.util.List;

public interface TransactionFilter {
    List<Integer> filter(List<Transaction> inputList);

}
