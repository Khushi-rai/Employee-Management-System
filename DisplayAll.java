package com.kodnest.EMSHibernate;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DisplayAll extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				DisplayAll frame = new DisplayAll();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public DisplayAll() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home();
				home.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(32, 320, 85, 21);
		contentPane.add(btnBack);

		JLabel lblTitle = new JLabel("List of All Employees");
		lblTitle.setBounds(220, 20, 200, 25);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 60, 480, 240);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		// Load employee data from DB
		loadEmployees();
	}

	private void loadEmployees() {
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			cfg.addAnnotatedClass(Employee.class);

			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();

			@SuppressWarnings("unchecked")
			List<Employee> list = session.createQuery("from Employee").list();

			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(new String[] { "ID", "Name", "Salary", "Department","Position"});

			for (Employee emp : list) {
				model.addRow(new Object[] {
					emp.getId(),
					emp.getName(),
					emp.getSalary(),
					emp.getDepartment(),
					emp.getPosition()
				});
			}

			table.setModel(model);

			session.close();
			factory.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}


