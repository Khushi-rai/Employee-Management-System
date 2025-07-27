package com.kodnest.EMSHibernate;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee Management System");
		lblNewLabel.setBounds(149, 10, 149, 28);
		contentPane.add(lblNewLabel);
		
		JButton DisplayAll = new JButton("View All Employees");
		DisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayAll display = new DisplayAll();
				display.setVisible(true);
				dispose();
				
			}
		});
		DisplayAll.setBounds(57, 80, 121, 21);
		contentPane.add(DisplayAll);
		
		JButton SingleEmployee = new JButton("View Employee");
		SingleEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleEmployee single = new SingleEmployee();
				single.setVisible(true);
				dispose();
			}
		});
		SingleEmployee.setBounds(275, 80, 121, 21);
		contentPane.add(SingleEmployee);
		
		JButton OnboardEmployee = new JButton("Add Employee");
		OnboardEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnboardEmployee onboard = new OnboardEmployee();
				onboard.setVisible(true);
				dispose();
			}
		});
		OnboardEmployee.setHorizontalAlignment(SwingConstants.LEFT);
		OnboardEmployee.setBounds(57, 140, 121, 21);
		contentPane.add(OnboardEmployee);
		
		JButton DeboardEmployee = new JButton("Delete Employee");
		DeboardEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeboardEmployee deboard = new DeboardEmployee();
				deboard.setVisible(true);
				dispose();
			}
		});
		DeboardEmployee.setBounds(275, 140, 121, 21);
		contentPane.add(DeboardEmployee);
		
		JButton UpdateEmployee = new JButton("Update Employee");
		UpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateEmployee update = new UpdateEmployee();
				update.setVisible(true);
				dispose();
			}
		});
		UpdateEmployee.setBounds(182, 203, 116, 21);
		contentPane.add(UpdateEmployee);

	}
}
