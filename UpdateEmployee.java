package com.kodnest.EMSHibernate;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UpdateEmployee extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;     // Search by ID field
	private JTextField textField_1;   // ID (non-editable)
	private JTextField textField_2;   // Name
	private JTextField textField_3;   // Salary
	private JTextField textField_4;   // Department
	private JTextField textField_5;   // Position

	private static SessionFactory factory;
	private Employee currentEmployee = null;

	static {
		factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Employee.class)
				.buildSessionFactory();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UpdateEmployee frame = new UpdateEmployee();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public UpdateEmployee() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Update Employee Details");
		lblTitle.setBounds(160, 10, 180, 20);
		contentPane.add(lblTitle);

		JLabel lblSearch = new JLabel("Employee ID:");
		lblSearch.setBounds(40, 45, 100, 13);
		contentPane.add(lblSearch);

		textField = new JTextField();
		textField.setBounds(130, 42, 150, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(310, 41, 90, 21);
		contentPane.add(btnSubmit);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(40, 80, 100, 13);
		contentPane.add(lblId);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(40, 110, 100, 13);
		contentPane.add(lblName);

		JLabel lblSalary = new JLabel("Salary:");
		lblSalary.setBounds(40, 140, 100, 13);
		contentPane.add(lblSalary);

		JLabel lblDept = new JLabel("Department:");
		lblDept.setBounds(40, 170, 100, 13);
		contentPane.add(lblDept);

		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setBounds(40, 200, 100, 13);
		contentPane.add(lblPosition);

		textField_1 = new JTextField(); // ID (non-editable)
		textField_1.setBounds(130, 77, 150, 20);
		textField_1.setEditable(false);
		contentPane.add(textField_1);

		textField_2 = new JTextField(); // Name
		textField_2.setBounds(130, 107, 150, 20);
		contentPane.add(textField_2);

		textField_3 = new JTextField(); // Salary
		textField_3.setBounds(130, 137, 150, 20);
		contentPane.add(textField_3);

		textField_4 = new JTextField(); // Department
		textField_4.setBounds(130, 167, 150, 20);
		contentPane.add(textField_4);

		textField_5 = new JTextField(); // Position
		textField_5.setBounds(130, 197, 150, 20);
		contentPane.add(textField_5);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(40, 240, 90, 25);
		contentPane.add(btnBack);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(300, 240, 100, 25);
		contentPane.add(btnUpdate);

		// Back Button
		btnBack.addActionListener(e -> {
			Home home = new Home();
			home.setVisible(true);
			dispose();
		});

		// Submit Button - Fetch Employee
		btnSubmit.addActionListener(e -> {
			int empId = Integer.parseInt(textField.getText());
			Session session = factory.openSession();
			currentEmployee = session.get(Employee.class, empId);
			session.close();

			if (currentEmployee != null) {
				textField_1.setText(String.valueOf(currentEmployee.getId()));
				textField_2.setText(currentEmployee.getName());
				textField_3.setText(String.valueOf(currentEmployee.getSalary()));
				textField_4.setText(currentEmployee.getDepartment());
				textField_5.setText(currentEmployee.getPosition());
			} else {
				JOptionPane.showMessageDialog(this, "Employee not found!");
				clearFields();
			}
		});

		// Update Button - Save changes
		btnUpdate.addActionListener(e -> {
			if (currentEmployee != null) {
				currentEmployee.setName(textField_2.getText());
				currentEmployee.setSalary(Integer.parseInt(textField_3.getText()));
				currentEmployee.setDepartment(textField_4.getText());
				currentEmployee.setPosition(textField_5.getText());

				Session session = factory.openSession();
				Transaction tx = session.beginTransaction();
				session.update(currentEmployee);
				tx.commit();
				session.close();

				JOptionPane.showMessageDialog(this, "Employee updated successfully!");
			} else {
				JOptionPane.showMessageDialog(this, "Please fetch an employee first.");
			}
		});
	}

	private void clearFields() {
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
	}
}


