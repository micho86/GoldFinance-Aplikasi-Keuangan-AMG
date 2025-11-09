import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GoldFinanceApp extends JFrame {
    private FinanceManager manager;
    private DefaultListModel<Transaction> model;
    private JList<Transaction> list;
    private JTextField dateField, descField, amountField;
    private JComboBox<String> typeBox;
    private JLabel totalLabel;

    public GoldFinanceApp() {
        manager = new FinanceManager();
        model = new DefaultListModel<>();

        setTitle("💰 GoldFinance — Keuangan Pribadi Elegan");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === HEADER PANEL (warna emas) ===
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 215, 0));
        JLabel title = new JLabel("💎 GoldFinance — Catatan Keuangan Pribadi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // === FORM INPUT ===
        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBackground(new Color(255, 248, 225));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        form.add(new JLabel("Tanggal (YYYY-MM-DD):"));
        dateField = new JTextField();
        form.add(dateField);

        form.add(new JLabel("Keterangan:"));
        descField = new JTextField();
        form.add(descField);

        form.add(new JLabel("Jumlah (Rp):"));
        amountField = new JTextField();
        form.add(amountField);

        form.add(new JLabel("Tipe Transaksi:"));
        typeBox = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        form.add(typeBox);

        JButton addBtn = new JButton("Tambah");
        JButton updateBtn = new JButton("Ubah");
        JButton deleteBtn = new JButton("Hapus");

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        // === LIST PANEL ===
        list = new JList<>(model);
        list.setBackground(new Color(255, 253, 245));
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(list);

        // === FOOTER TOTAL ===
        JPanel footer = new JPanel();
        footer.setBackground(new Color(255, 248, 225));
        totalLabel = new JLabel("Total Saldo: Rp 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        footer.add(totalLabel);

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(footer, BorderLayout.PAGE_END);

        // === EVENT HANDLER ===
        addBtn.addActionListener(e -> {
            try {
                Transaction t = new Transaction(
                        dateField.getText(),
                        descField.getText(),
                        Double.parseDouble(amountField.getText()),
                        typeBox.getSelectedItem().toString());
                manager.addTransaction(t);
                model.addElement(t);
                updateTotal();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        });

        updateBtn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                Transaction t = new Transaction(
                        dateField.getText(),
                        descField.getText(),
                        Double.parseDouble(amountField.getText()),
                        typeBox.getSelectedItem().toString());
                manager.updateTransaction(index, t);
                model.setElementAt(t, index);
                updateTotal();
                clearFields();
            }
        });

        deleteBtn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                manager.deleteTransaction(index);
                model.remove(index);
                updateTotal();
                clearFields();
            }
        });

        list.addListSelectionListener(e -> {
            Transaction t = list.getSelectedValue();
            if (t != null) {
                dateField.setText(t.getDate());
                descField.setText(t.getDescription());
                amountField.setText(String.valueOf(t.getAmount()));
                typeBox.setSelectedItem(t.getType());
            }
        });

        setVisible(true);
    }

    private void updateTotal() {
        totalLabel.setText("Total Saldo: Rp " + manager.getTotalBalance());
    }

    private void clearFields() {
        dateField.setText("");
        descField.setText("");
        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GoldFinanceApp::new);
    }
}
