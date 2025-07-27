package com.kodnest.EMSHibernate;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class DeboardEmployee extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTable table;
    private static SessionFactory factory;

    static {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DeboardEmployee frame = new DeboardEmployee();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DeboardEmployee() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Employee Exit");
        lblTitle.setBounds(180, 10, 150, 22);
        contentPane.add(lblTitle);

        JLabel lblID = new JLabel("Employee ID:");
        lblID.setBounds(30, 50, 100, 20);
        contentPane.add(lblID);

        textField = new JTextField();
        textField.setBounds(120, 50, 150, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(300, 50, 100, 22);
        contentPane.add(btnSubmit);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 90, 420, 120);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(30, 230, 100, 25);
        contentPane.add(btnBack);

        JButton btnDeboard = new JButton("Confirm Deboard");
        btnDeboard.setBounds(300, 230, 150, 25);
        contentPane.add(btnDeboard);

        // Action: Show Employee Info
        btnSubmit.addActionListener(e -> {
            int id = Integer.parseInt(textField.getText());
            showEmployee(id);
        });

        // Action: Delete Employee
        btnDeboard.addActionListener(e -> {
            int id = Integer.parseInt(textField.getText());
            boolean result = deleteEmployee(id);
            if (result) {
                JOptionPane.showMessageDialog(this, "Deboarded Successfully");
                clearTable();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        });

        btnBack.addActionListener(e -> {
            Home home = new Home();
            home.setVisible(true);
            dispose();
        });
    }

    // Show Employee details in table
    private void showEmployee(int id) {
        Session session = factory.openSession();
        Employee emp = session.get(Employee.class, id);
        session.close();

        if (emp != null) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Salary");
            model.addColumn("Department");
            model.addColumn("Position");
           

            Vector<String> row = new Vector<>();
            row.add(String.valueOf(emp.getId()));
            row.add(emp.getName());
            row.add(String.valueOf(emp.getSalary()));
            row.add(emp.getDepartment());
            row.add(emp.getPosition());
     
            model.addRow(row);
            table.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found!");
            clearTable();
        }
    }

    // Delete employee from database
    private boolean deleteEmployee(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Employee emp = session.get(Employee.class, id);

        if (emp != null) {
            session.delete(emp);
            tx.commit();
            session.close();
            return true;
        } else {
            tx.rollback();
            session.close();
            return false;
        }
    }

    private void clearTable() {
        table.setModel(new DefaultTableModel());
    }
}

