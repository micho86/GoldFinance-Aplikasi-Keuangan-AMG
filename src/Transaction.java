import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String description;
    private double amount;
    private String type; // "Pemasukan" atau "Pengeluaran"

    public Transaction(LocalDate date, String description, double amount, String type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public double getEffectiveAmount() {
        return type.equalsIgnoreCase("Pemasukan") ? amount : -amount;
    }
}
