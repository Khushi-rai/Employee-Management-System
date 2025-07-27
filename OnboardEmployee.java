package com.kodnest.EMSHibernate;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OnboardEmployee extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfSalary;
	private JTextField tfDepartment;
	private JTextField tfPosition;

	private static SessionFactory factory;

	static {
		try {
			factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Employee.class)
				.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				OnboardEmployee frame = new OnboardEmployee();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public OnboardEmployee() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Employee Onboarding");
		lblTitle.setBounds(150, 10, 150, 20);
		contentPane.add(lblTitle);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(65, 52, 100, 13);
		contentPane.add(lblName);

		JLabel lblSalary = new JLabel("Salary:");
		lblSalary.setBounds(65, 90, 100, 13);
		contentPane.add(lblSalary);

		JLabel lblDepartment = new JLabel("Department:");
		lblDepartment.setBounds(65, 132, 100, 13);
		contentPane.add(lblDepartment);

		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setBounds(65, 173, 100, 13);
		contentPane.add(lblPosition);

		tfName = new JTextField();
		tfName.setBounds(162, 49, 184, 19);
		contentPane.add(tfName);
		tfName.setColumns(10);

		tfSalary = new JTextField();
		tfSalary.setBounds(162, 87, 184, 19);
		contentPane.add(tfSalary);
		tfSalary.setColumns(10);

		tfDepartment = new JTextField();
		tfDepartment.setBounds(162, 129, 184, 19);
		contentPane.add(tfDepartment);
		tfDepartment.setColumns(10);

		tfPosition = new JTextField();
		tfPosition.setBounds(162, 170, 184, 19);
		contentPane.add(tfPosition);
		tfPosition.setColumns(10);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(76, 210, 85, 25);
		btnBack.addActionListener(e -> {
			new Home().setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);

		JButton btnOnboard = new JButton("Onboard");
		btnOnboard.setBounds(261, 210, 100, 25);
		btnOnboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = tfName.getText();
					int salary = Integer.parseInt(tfSalary.getText());
					String department = tfDepartment.getText();
					String position = tfPosition.getText();

					Session session = factory.openSession();
					Transaction tx = session.beginTransaction();

					Employee emp = new Employee(name, salary, department, position);
					session.persist(emp);

					tx.commit();
					session.close();

					JOptionPane.showMessageDialog(null, "Employee onboarded successfully!");
					clearFields();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		contentPane.add(btnOnboard);
	}

	private void clearFields() {
		tfName.setText("");
		tfSalary.setText("");
		tfDepartment.setText("");
		tfPosition.setText("");
	}
}
