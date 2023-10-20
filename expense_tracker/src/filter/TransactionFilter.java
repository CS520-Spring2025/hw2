package filter;

import model.Transaction;

import java.util.List;

public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> inputList);

}
