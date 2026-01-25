import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class InventoryManagementSystem extends JFrame {

    JTextField txtId, txtName, txtQty, txtPrice;
    JTable table;
    DefaultTableModel model;

    public InventoryManagementSystem() {

        setTitle("Inventory Management System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Item ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Item Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Quantity:"));
        txtQty = new JTextField();
        panel.add(txtQty);

        panel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        panel.add(txtPrice);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        panel.add(btnAdd);
        panel.add(btnUpdate);

        add(panel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add(btnDelete, BorderLayout.SOUTH);

        // Add Button Event
        btnAdd.addActionListener(e -> {
            model.addRow(new Object[] {
                    txtId.getText(),
                    txtName.getText(),
                    txtQty.getText(),
                    txtPrice.getText()
            });
            clearFields();
        });

        // Update Button Event
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                model.setValueAt(txtId.getText(), row, 0);
                model.setValueAt(txtName.getText(), row, 1);
                model.setValueAt(txtQty.getText(), row, 2);
                model.setValueAt(txtPrice.getText(), row, 3);
                clearFields();
            }
        });

        // Delete Button Event
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                model.removeRow(row);
            }
        });

        // Table Row Click Event
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtQty.setText(model.getValueAt(row, 2).toString());
                txtPrice.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtPrice.setText("");
    }

    public static void main(String[] args) {
        new InventoryManagementSystem().setVisible(true);
    }
}
