package model.filters;

import java.util.List;
import model.Transaction;

public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);

}
