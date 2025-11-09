import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GoldFinanceApp extends JFrame {
    private final FinanceManager financeManager;
    private final JTextField txtDate, txtDescription, txtAmount;
    private final JComboBox<String> cbType;
    private final JLabel lblBalance;
    private final DefaultTableModel tableModel;

    public GoldFinanceApp() {
        financeManager = new FinanceManager();

        setTitle("GoldFinance — Keuangan Pribadi Elegan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 230));
        add(panel);

        // --- Panel input ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(255, 250, 230));

        inputPanel.add(new JLabel("Tanggal (YYYY-MM-DD):"));
        txtDate = new JTextField();
        inputPanel.add(txtDate);

        inputPanel.add(new JLabel("Keterangan:"));
        txtDescription = new JTextField();
        inputPanel.add(txtDescription);

        inputPanel.add(new JLabel("Jumlah (Rp):"));
        txtAmount = new JTextField();
        inputPanel.add(txtAmount);

        inputPanel.add(new JLabel("Tipe Transaksi:"));
        cbType = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        inputPanel.add(cbType);

        JButton btnAdd = new JButton("Tambah Transaksi");
        btnAdd.addActionListener(e -> addTransaction());
        inputPanel.add(btnAdd);

        panel.add(inputPanel, BorderLayout.NORTH);

        // --- Tabel histori transaksi ---
        String[] columns = {"Tanggal", "Keterangan", "Jumlah (Rp)", "Tipe"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // --- Label saldo ---
        lblBalance = new JLabel("Total Saldo: Rp 0", SwingConstants.CENTER);
        lblBalance.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBalance.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblBalance, BorderLayout.SOUTH);
    }

    private void addTransaction() {
        try {
            LocalDate date = LocalDate.parse(txtDate.getText().trim());
            String desc = txtDescription.getText().trim();
            double amount = Double.parseDouble(txtAmount.getText().trim());
            String type = (String) cbType.getSelectedItem();

            Transaction transaction = new Transaction(date, desc, amount, type);
            financeManager.addTransaction(transaction);

            // Tambahkan ke tabel
            tableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getDescription(),
                    String.format("Rp %, .0f", transaction.getAmount()),
                    transaction.getType()
            });

            // Update saldo
            double total = financeManager.getTotalBalance();
            lblBalance.setText(String.format("Total Saldo: Rp %, .0f", total));

            // Bersihkan input
            txtDescription.setText("");
            txtAmount.setText("");

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GoldFinanceApp().setVisible(true));
    }
}

