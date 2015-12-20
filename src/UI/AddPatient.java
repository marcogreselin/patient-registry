package UI;

import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dbcalls.DataBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import profiles.MedicalCondition;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

/**
 * Creates the frame to add a new patient.
 * @author marco
 * @see Patients
 */
public class AddPatient extends JFrame {
	
	String firstName;
	String lastName;
	String DOB;
	String address;
	String phone;
	String medicalCondition;
	String billing;
	String comments;
	String mainPhoto;

	private JPanel contentPane;
	private JTextField inFirst;
	private JTextField inLast;
	private JTextField inDOB;
	private JTextField inAddress;
	private JTextField inPhone;
	private JTextField inBilling;
	private JTextField inComments;
	private JFileChooser fc;
	JLabel lblProfilePicName;
	JComboBox inMedicalCondition;
	DataBase db = new DataBase();


	/**
	 * Launch the application.
	 */
	public static void launchAP() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPatient frame = new AddPatient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Used to add a new patient from {@link Patients}.
	 */
	public AddPatient() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 444, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblFirstName.setBounds(53, 35, 114, 28);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblLastName.setBounds(53, 75, 114, 28);
		contentPane.add(lblLastName);
		
		JLabel lblDOB = new JLabel("Date of Birth:");
		lblDOB.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblDOB.setBounds(53, 115, 114, 28);
		contentPane.add(lblDOB);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblAddress.setBounds(53, 155, 114, 28);
		contentPane.add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblPhone.setBounds(53, 195, 114, 28);
		contentPane.add(lblPhone);
		
		JLabel lblMedicalCondition = new JLabel("Medical Condition:");
		lblMedicalCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblMedicalCondition.setBounds(53, 235, 147, 28);
		contentPane.add(lblMedicalCondition);
		
		JLabel lblBilling = new JLabel("Billing:");
		lblBilling.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblBilling.setBounds(53, 275, 147, 28);
		contentPane.add(lblBilling);
		
		JLabel lblComments = new JLabel("Comments:");
		lblComments.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblComments.setBounds(53, 316, 147, 28);
		contentPane.add(lblComments);
		
		JLabel lblProfilePicture = new JLabel("Profile Picture:");
		lblProfilePicture.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblProfilePicture.setBounds(53, 356, 147, 28);
		contentPane.add(lblProfilePicture);
		
//		JLabel lblOtherPictures = new JLabel("Other Pictures:");
//		lblOtherPictures.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		lblOtherPictures.setBounds(155, 396, 147, 28);
//		contentPane.add(lblOtherPictures);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="INSERT INTO 'main'.'patients' ('firstName','lastName','DOB','address','phone','medicalCondition','billing','comments','mainPhoto') VALUES"
					+"('"+inFirst.getText()+"',"
					+"'"+inLast.getText()+"',"
					+"'"+inDOB.getText()+"',"
					+"'"+inAddress.getText()+"',"
					+"'" +inPhone.getText()+"',"
					+"'" +String.valueOf(inMedicalCondition.getSelectedItem())+"',"
					+"'"+inBilling.getText()+"',"
					+"'"+inComments.getText()+"',"
					+"'"+fc.getSelectedFile().getName()+"');";
					if(
						inFirst.getText().length()>0 &&
						inLast.getText().length()>0 &&
						inDOB.getText().length()>0 &&
						inAddress.getText().length()>0 &&
						inPhone.getText().length()>0 &&
						inBilling.getText().length()>0 &&
						inComments.getText().length()>0 &&
						fc.getSelectedFile().getAbsolutePath() != null ){
							
							Files.copy(Paths.get(fc.getSelectedFile().getAbsolutePath()),Paths.get("img/"+fc.getSelectedFile().getName()));
							db.AddPatient(query);
							setVisible(false);
							HomePage.updatePatients(db.Patients());
					} else{
						JOptionPane.showMessageDialog(null, "Complete all fields!");
					}
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Complete all fields!");
				}
				
			}
		});
		
		btnAdd.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnAdd.setBounds(27, 435, 117, 40);
		contentPane.add(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnCancel.setBounds(295, 435, 117, 40);
		contentPane.add(btnCancel);
		
		inFirst = new JTextField();
		inFirst.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inFirst.setBounds(196, 37, 216, 26);
		contentPane.add(inFirst);
		inFirst.setColumns(10);
		firstName=inFirst.getText();
		
		inLast = new JTextField();
		inLast.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inLast.setColumns(10);
		inLast.setBounds(196, 77, 216, 26);
		contentPane.add(inLast);
		lastName=inLast.getText();
		
		inDOB = new JTextField();
		inDOB.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inDOB.setColumns(10);
		inDOB.setBounds(196, 117, 216, 26);
		contentPane.add(inDOB);
		DOB=inDOB.getText();
		
		inAddress = new JTextField();
		inAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inAddress.setColumns(10);
		inAddress.setBounds(196, 157, 216, 26);
		contentPane.add(inAddress);
		address=inAddress.getText();
		
		inPhone = new JTextField();
		inPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inPhone.setColumns(10);
		inPhone.setBounds(196, 197, 216, 26);
		contentPane.add(inPhone);
		phone=inPhone.getText();
		
		inBilling = new JTextField();
		inBilling.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inBilling.setColumns(10);
		inBilling.setBounds(196, 277, 216, 26);
		contentPane.add(inBilling);
		billing=inBilling.getText();
		
		inComments = new JTextField();
		inComments.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inComments.setColumns(10);
		inComments.setBounds(196, 318, 216, 26);
		contentPane.add(inComments);
		comments=inComments.getText();
		
		JButton btnSelectFile = new JButton("Select File");
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
		fc.setFileFilter(filter);
		
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
					mainPhoto=fc.getSelectedFile().getAbsolutePath();
					lblProfilePicName.setText(fc.getSelectedFile().getAbsolutePath());
				} else {
					JOptionPane.showMessageDialog(null, "Select a file!");
				}
			}
		});
		btnSelectFile.setBounds(295, 358, 117, 29);
		contentPane.add(btnSelectFile);
		
		lblProfilePicName = new JLabel("no file");
		lblProfilePicName.setBounds(196, 363, 94, 16);
		contentPane.add(lblProfilePicName);
		
		inMedicalCondition = new JComboBox(profiles.MedicalCondition.createNames());
		inMedicalCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		inMedicalCondition.setBounds(212, 235, 200, 31);
		contentPane.add(inMedicalCondition);
		medicalCondition=String.valueOf(inMedicalCondition.getSelectedItem());
	}
}
