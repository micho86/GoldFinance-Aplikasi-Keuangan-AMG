import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private final List<Transaction> transactions;

    public FinanceManager() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getTotalBalance() {
        return transactions.stream()
                .mapToDouble(Transaction::getEffectiveAmount)
                .sum();
    }
}
