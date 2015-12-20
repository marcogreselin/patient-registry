package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dbcalls.DataBase;
/**
 * Has the elements needed to fill the central and lower part of the {@link HomePage} regarding patients.
 * The constructor will return a {code JPanel} that we will add to the {@code JPanel}.
 * @author marco
 * @see HomePage
 */
public class Patients  {
	static JTable patientsTable;
	public int selectedPatientId;
	public JButton btnSearch, addPatientsButtion;
	public static JPanel patientsPanel;
	static String[] patientsColumns= {
			"First Name", 
			"Last Name",
			"DOB"};;
	
	/**
	 * Called to return the {@code JPanel}.
	 * @param input normal setting the whole list of patients. Otherwise the results of a search.
	 * @return the panel that will be attached to the HP.
	 */
	public JPanel launchPatients(ArrayList<String[]> input){
		patientsPanel = new JPanel();
		patientsPanel.setBounds(6, 152, 882, 417);
		
		patientsPanel.setLayout(null);
		
		ArrayList<String[]> patientsQueryResults=input;
		String[][] tableInput = new String[patientsQueryResults.size()][]; 
		for (int i = 0; i < tableInput.length; i++) { 
			String[] row = patientsQueryResults.get(i); 
			tableInput[i] = row;
		}

		/**
		 * Disables editing from the {@code JTable}.
		 */
		patientsTable = new JTable(tableInput,patientsColumns){
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
		patientsScroll.setViewportView(patientsTable);
		patientsPanel.add(patientsScroll);
		
		/**
		 * Calls the patients input form in case a patient is double clicked.
		 * Inspired by <a href="http://stackoverflow.com/questions/1378096/actionlistener-on-jlabel-or-jtable-cell">this</a> article.
		 */
		patientsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable)e.getSource();
					int r = target.getSelectedRow();
					int c = target.getSelectedColumn();
					try{
						PatientForm pf = new PatientForm(Integer.valueOf(patientsQueryResults.get(target.getSelectedRow())[3]));
						pf.showPatientCard();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Something went wrong.");
					}
				}
			}
		});
		
		addPatientsButtion = new JButton("Add New Patient");
		addPatientsButtion.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		addPatientsButtion.setBounds(155, 375, 211, 36);
		addPatientsButtion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				AddPatient ap = new AddPatient();
				ap.launchAP();
			}
		});
		patientsPanel.add(addPatientsButtion);
		
		btnSearch = new JButton("Search Patient");
		btnSearch.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnSearch.setBounds(553, 375, 211, 36);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SearchPatient sp= new SearchPatient();
				sp.launchSearchPatients();
			}
		});
		patientsPanel.add(btnSearch);	

		return patientsPanel;
	}
	
}
