public class Transaction {
    private String date;
    private String description;
    private double amount;
    private String type; // "Pemasukan" atau "Pengeluaran"

    public Transaction(String date, String description, double amount, String type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getDate() { return date; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getType() { return type; }

    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return date + " | " + description + " | " + type + " | Rp " + amount;
    }
}
