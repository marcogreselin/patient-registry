package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbcalls.DataBase;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
/**
 * Creates the frame to add a new appointment.
 * @author marco
 * @see Appointments
 */
public class AddAppointment extends JFrame {

	private JPanel contentPane;
	private String[] patientsList;
	private JTextField tfTime;
	private JTextField tfDate;
	private JTextField tfComments;
	DataBase db = new DataBase();


	/**
	 * Launch the application.
	 */
	public static void launchAddAppointment() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAppointment frame = new AddAppointment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Used to create a new appointment. Accessed from the panel in {@link UI.Appointments}.
	 */
	public AddAppointment() {
		ArrayList<String[]> rawList = db.Patients();
		patientsList = new String[rawList.size()];
		for(int i =0; i<rawList.size();i++){
			patientsList[i]=rawList.get(i)[0]+" "+rawList.get(i)[1];
		}
		
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox(patientsList);
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		comboBox.setBounds(77, 31, 285, 57);
		contentPane.add(comboBox);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblTime.setBounds(64, 96, 112, 29);
		contentPane.add(lblTime);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblDate.setBounds(64, 137, 112, 29);
		contentPane.add(lblDate);
		
		JLabel lblComments = new JLabel("Comments");
		lblComments.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblComments.setBounds(64, 178, 112, 29);
		contentPane.add(lblComments);
		
		JButton btnAdd = new JButton("Add!");
		btnAdd.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnAdd.setBounds(169, 219, 117, 49);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query="INSERT INTO 'main'.'appointments' ('patientId','date','time','comments') VALUES"
				+"('"+rawList.get(comboBox.getSelectedIndex())[3]+"',"
				+"'"+tfDate.getText()+"',"
				+"'"+tfTime.getText()+"',"
				+"'"+tfComments.getText()+"');";
				if(db.AddPatient(query)==true){
						setVisible(false);
						HomePage.updateAppointments(db.Appointments());
				}
			}
		});
		contentPane.add(btnAdd);
		
		JLabel lblSelectPatient = new JLabel("Select Patient");
		lblSelectPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectPatient.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblSelectPatient.setBounds(139, 6, 158, 29);
		contentPane.add(lblSelectPatient);
		
		tfTime = new JTextField();
		tfTime.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		tfTime.setBounds(225, 99, 158, 26);
		contentPane.add(tfTime);
		tfTime.setColumns(10);
		
		tfDate = new JTextField();
		tfDate.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		tfDate.setColumns(10);
		tfDate.setBounds(225, 140, 158, 26);
		contentPane.add(tfDate);
		
		tfComments = new JTextField();
		tfComments.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		tfComments.setColumns(10);
		tfComments.setBounds(225, 181, 158, 26);
		contentPane.add(tfComments);
	}
}
