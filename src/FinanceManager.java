import java.util.ArrayList;

public class FinanceManager {
    private ArrayList<Transaction> transactions;

    public FinanceManager() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void updateTransaction(int index, Transaction t) {
        transactions.set(index, t);
    }

    public void deleteTransaction(int index) {
        transactions.remove(index);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public double getTotalBalance() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Pemasukan")) {
                total += t.getAmount();
            } else {
                total -= t.getAmount();
            }
        }
        return total;
    }
}
