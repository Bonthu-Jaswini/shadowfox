import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentManagementSystem extends JFrame {

    JTextField txtName, txtCourse, txtAge;
    JButton btnAdd, btnUpdate, btnDelete, btnClear;
    JTable table;
    DefaultTableModel model;

    ArrayList<Student> students = new ArrayList<>();
    int idCounter = 1;
    int selectedRow = -1;

    public StudentManagementSystem() {

        setTitle("Student Information System");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== Top Panel =====
        JLabel title = new JLabel("Student Information System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.add(title);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        txtName = new JTextField();
        txtCourse = new JTextField();
        txtAge = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(txtCourse);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // ===== Table =====
        model = new DefaultTableModel(new String[] { "ID", "Name", "Course", "Age" }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Layout =====
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== Button Events =====

        btnAdd.addActionListener(e -> {
            String name = txtName.getText();
            String course = txtCourse.getText();
            int age = Integer.parseInt(txtAge.getText());

            Student s = new Student(idCounter++, name, course, age);
            students.add(s);

            model.addRow(new Object[] { s.id, s.name, s.course, s.age });
            clearFields();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
                txtName.setText(model.getValueAt(selectedRow, 1).toString());
                txtCourse.setText(model.getValueAt(selectedRow, 2).toString());
                txtAge.setText(model.getValueAt(selectedRow, 3).toString());
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedRow >= 0) {
                model.setValueAt(txtName.getText(), selectedRow, 1);
                model.setValueAt(txtCourse.getText(), selectedRow, 2);
                model.setValueAt(txtAge.getText(), selectedRow, 3);
                clearFields();
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedRow >= 0) {
                model.removeRow(selectedRow);
                selectedRow = -1;
                clearFields();
            }
        });

        btnClear.addActionListener(e -> clearFields());
    }

    void clearFields() {
        txtName.setText("");
        txtCourse.setText("");
        txtAge.setText("");
    }

    public static void main(String[] args) {
        new StudentManagementSystem().setVisible(true);
    }
}
