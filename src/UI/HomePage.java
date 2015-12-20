package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dbcalls.DataBase;
import main.MainClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Implements the main JFrame to which the three UIs are added. There are {@link UI.Appointments}, 
 * {@link UI.Appointments} and {@code UI.ImportExport}. 
 * @author marco
 *
 */
public class HomePage extends JFrame {

	static JPanel contentPane;
	static JPanel variablePane;
	DataBase db = new DataBase();

	

	/**
	 * Use {code invokeLater()} so that the application is built before it's displayed. 
	 */
	public  void launchHP() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patients p = new Patients();
					variablePane = p.launchPatients(db.Patients());
					contentPane.add(variablePane);
					contentPane.setVisible(true);
					contentPane.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This is the main Window in the program. It updates based on user actions.
	 * It presents a three buttons to access all major functions.
	 */
	public HomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 894, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sanus - Practitioner: "+MainClass.userFullName);
		lblNewLabel.setBounds(29, 17, 730, 58);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);
		
		JButton btnPatients = new JButton("Patients");
		btnPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(variablePane);
				Patients a = new Patients();
				variablePane = a.launchPatients(db.Patients());
				contentPane.add(variablePane);
				contentPane.repaint();
			}
		});
		btnPatients.setBounds(77, 86, 184, 50);
		btnPatients.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		contentPane.add(btnPatients);
		
		JButton btnAppointments = new JButton("Appointments");
		btnAppointments.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(variablePane);
				Appointments a = new Appointments();
				variablePane = a.launchAppointments(db.Appointments());
				contentPane.add(variablePane);
				contentPane.repaint();
			}
		});
		btnAppointments.setBounds(357, 86, 184, 50);
		btnAppointments.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		contentPane.add(btnAppointments);
		
		JButton btnImportexport = new JButton("Import/Export");
		btnImportexport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(variablePane);
				ImportExport ie=new ImportExport();
				variablePane =ie.launchImportExport();
				contentPane.add(variablePane);
				contentPane.repaint();
			}
		});
		btnImportexport.setBounds(629, 86, 184, 50);
		btnImportexport.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		contentPane.add(btnImportexport);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(771, 27, 117, 29);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LoginPage lp = new LoginPage();
				lp.frame.setVisible(true);
			}
		});
		contentPane.add(btnLogOut);	
		
		setVisible(true);
	}
	
	/**
	 * Called to update the panel with the patients after changes are made in other dialog boxes.
	 * @see UI.AddPatient
	 * @param inputUpdate the new elements to stuff the table.
	 */
	public static void updatePatients(ArrayList<String[]> inputUpdate){
		contentPane.remove(variablePane);
		Patients a = new Patients();
		variablePane = a.launchPatients(inputUpdate);
		contentPane.add(variablePane);
		contentPane.repaint();
	}
	
	/**
	 * Called to update the panel with the appointments after changes are made in other dialog boxes.
	 * @param inputUpdate the new stuff for the appointments table
	 */
	public static void updateAppointments(ArrayList<String[]> inputUpdate){
		contentPane.remove(variablePane);
		Appointments a = new Appointments();
		variablePane = a.launchAppointments(inputUpdate);
		contentPane.add(variablePane);
		contentPane.repaint();
	}
	
}
