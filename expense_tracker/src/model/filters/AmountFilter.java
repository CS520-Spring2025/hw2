package model.filters;

import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {

    private double targetAmount;

    public AmountFilter(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() == targetAmount)
                .collect(Collectors.toList());
    }
}
