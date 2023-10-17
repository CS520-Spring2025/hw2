package model.filters;


import model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter {

    private String targetCategory;

    public CategoryFilter(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(targetCategory))
                .collect(Collectors.toList());
    }
}

