package ui;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.PostgresqlDb;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Activities extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField nameField;
	private JTextField amountField;
	private JTextField dateField;
	private static DefaultTableModel defaultTableModel = new DefaultTableModel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Activities frame = new Activities();
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
	public Activities() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 350);
		setTitle("Account Activities");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(50, 50, 500, 250);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(getTableModel());
		
		nameField = new JTextField();
		nameField.setBounds(680, 80, 170, 30);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(680, 130, 170, 30);
		contentPane.add(amountField);
		
		dateField = new JTextField();
		dateField.setColumns(10);
		dateField.setBounds(680, 180, 170, 30);
		contentPane.add(dateField);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(600, 85, 70, 20);
		contentPane.add(lblName);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(600, 135, 70, 20);
		contentPane.add(lblAmount);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(600, 185, 70, 20);
		contentPane.add(lblDate);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nameField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				amountField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				dateField.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String insertName, insertAmount, insertDate;
				insertName = nameField.getText();
				insertAmount = amountField.getText();
				insertDate = dateField.getText();
				
				PostgresqlDb.addActivities(insertName, insertAmount, insertDate);
				setTableRows();
				
				nameField.setText("");
				amountField.setText("");
				dateField.setText("");
				
			}
		});
		btnSave.setBounds(600, 235, 90, 25);
		contentPane.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deleteName, deleteAmount, deleteDate;
				deleteName = nameField.getText();
				deleteAmount = amountField.getText();
				deleteDate = dateField.getText();
				
				PostgresqlDb.deleteActivities(deleteName, deleteAmount, deleteDate);
				setTableRows();
				
				nameField.setText("");
				amountField.setText("");
				dateField.setText("");
				
			}
		});
		btnDelete.setBounds(700, 235, 90, 25);
		contentPane.add(btnDelete);
		
		JButton btnKaydet_1_1 = new JButton("Update");
		btnKaydet_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String updateName, updateAmount, updateDate;
				updateName = nameField.getText();
				updateAmount = amountField.getText();
				updateDate = dateField.getText();
				
				PostgresqlDb.updateActivities(updateName, updateAmount, updateDate);
				setTableRows();
				
				nameField.setText("");
				amountField.setText("");
				dateField.setText("");
				
			}
		});
		btnKaydet_1_1.setBounds(800, 235, 90, 25);
		contentPane.add(btnKaydet_1_1);
	}
	
	private static DefaultTableModel getTableModel() {
		
		String[] columns = {"Name", "Amount", "Date"};
		defaultTableModel.setColumnCount(3);
		
		defaultTableModel.setColumnIdentifiers(columns);

		setTableRows();	
		return defaultTableModel;
		
	}
	
	private static void setTableRows() {

		Object[] rows = new Object[3];
		defaultTableModel.setRowCount(0);
		try {
			ResultSet resultSet = PostgresqlDb.getAllActivities();
			while(resultSet.next()) {
				rows[0] = resultSet.getString("name");
				rows[1] = resultSet.getLong("amount");
				rows[2] = resultSet.getDate("date");
				defaultTableModel.addRow(rows);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
