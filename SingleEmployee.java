package com.kodnest.EMSHibernate;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleEmployee extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTable table;
    public static SessionFactory factory;

    static {
        factory = new Configuration().configure("hibernate.cfg.xml")
                                     .addAnnotatedClass(Employee.class)
                                     .buildSessionFactory();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SingleEmployee frame = new SingleEmployee();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SingleEmployee() {
        setTitle("View Single Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Employee Details");
        lblTitle.setBounds(180, 10, 200, 20);
        contentPane.add(lblTitle);

        JLabel lblId = new JLabel("Employee ID:");
        lblId.setBounds(40, 40, 100, 20);
        contentPane.add(lblId);

        textField = new JTextField();
        textField.setBounds(142, 40, 182, 27);
        contentPane.add(textField);
        textField.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(57, 107, 370, 120);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            Home home = new Home();
            home.setVisible(true);
            dispose();
        });
        btnBack.setBounds(21, 282, 85, 21);
        contentPane.add(btnBack);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(340, 40, 90, 27);
        contentPane.add(btnSearch);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                try {
                    int empId = Integer.parseInt(input);
                    fetchEmployee(empId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric Employee ID.");
                }
            }
        });
    }

    private void fetchEmployee(int empId) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee employee = session.get(Employee.class, empId);
            tx.commit();

            if (employee != null) {
                String[] columnNames = {"ID", "Name", "Salary", "Department", "Position"};
                Object[][] rowData = {
                    {employee.getId(), employee.getName(), employee.getSalary(), employee.getDepartment(), employee.getPosition()}
                };
                table.setModel(new DefaultTableModel(rowData, columnNames));
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found.");
                table.setModel(new DefaultTableModel());
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

