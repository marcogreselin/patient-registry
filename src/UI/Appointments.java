package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dbcalls.DataBase;
/**
 * Creates the {@code JPanel} that we will attach to the Home with 
 * information about appointments (A table and a couple of buttons).
 * @see HomePage
 */
public class Appointments {
	JTable appointmentsTable;
	public int selectedPatientId;
	
	public JPanel launchAppointments(ArrayList<String[]> input){
		JPanel appointmentsPanel = new JPanel();
		appointmentsPanel.setBounds(6, 152, 882, 417);
		appointmentsPanel.setLayout(null);
		
		String[] patientsColumns = {
				"Last Name", 
				"Date",
				"Time"};
		ArrayList<String[]> appointmentsQueryResults=input;
		String[][] tableInput = new String[appointmentsQueryResults.size()][]; 
		for (int i = 0; i < tableInput.length; i++) { 
			String[] row = appointmentsQueryResults.get(i); 
			tableInput[i] = row;
		}

		/**
		 * Disables editing from the {@code JTable}.
		 */
		appointmentsTable = new JTable(tableInput,patientsColumns){
			public boolean isCellEditable(int tableInput,int patientsColumns){
				return false;
			}
		};
		/**
		 * Received help on StackOverflow on not defining setLayout. 
		 * More <a href="http://stackoverflow.com/questions/33857192/jtable-in-jpanel-not-displaying#33857368">here</a>.
		 */
		JScrollPane patientsScroll = new JScrollPane();
		patientsScroll.setBounds(0, 0, 882, 363);
		patientsScroll.setViewportView(appointmentsTable);
		appointmentsPanel.add(patientsScroll);
		
		JButton addAppointmentButton = new JButton("Add New Appoint.");
		addAppointmentButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		addAppointmentButton.setBounds(155, 375, 211, 36);
		addAppointmentButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAppointment aa= new AddAppointment();
				aa.launchAddAppointment();
				
			}
		});
		appointmentsPanel.add(addAppointmentButton);

		
		JButton btnSearchA = new JButton("Search Appoint.");
		btnSearchA.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnSearchA.setBounds(553, 375, 211, 36);
		btnSearchA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SearchAppointment sa= new SearchAppointment();
				sa.launchSearchAppointments();
			}
		});
		appointmentsPanel.add(btnSearchA);
		
		/**
		 * Calls the patients input form in case a patient is double clicked.
		 * Inspired by <a href="http://stackoverflow.com/questions/1378096/actionlistener-on-jlabel-or-jtable-cell">this</a> article.
		 */
		appointmentsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable)e.getSource();
					int r = target.getSelectedRow();
					int c = target.getSelectedColumn();
					AppointmentForm pf = new AppointmentForm(Integer.valueOf(appointmentsQueryResults.get(target.getSelectedRow())[3]));
					pf.showAppointmentCard();
				}
			}
		});
		return appointmentsPanel;		
	}
}
