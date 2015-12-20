package UI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import dbcalls.DataBase;
import profiles.MedicalCondition;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Creates the frame to see all the data for a specific Patient.
 * @author marco
 *	@see Patients
 */
public class PatientForm  {
	private JFrame mainFrame= new JFrame();
	private JPanel contentPane;
	JLabel lblName = new JLabel("Name: ");
	JButton btnDeletePatient = new JButton("Delete Patient");
	JLabel lblDOB = new JLabel("Date of Birth:");
	JLabel lblAddress = new JLabel("Address:");
	JLabel lblPhone = new JLabel("Phone:");
	JLabel lblMedicalCondition = new JLabel("Medical Condition");
	JLabel lblComments = new JLabel("Comments:");
	JLabel lblBilling = new JLabel("Billing:");
	JButton btnOtherPhotos = new JButton("Other Photos");
	private JLabel lblMainPhoto = new JLabel("");
	public int id;
	JLabel clickHere;
	DataBase db = new DataBase();


	/**
	 * Creates the form displaying info about a patient.
	 * Elements can be clicked to edit them. Also possible to delete a patient and access more pics.
	 * @see MorePhotos
	 * @see Patients
	 * @param id patient ID from the {@code JTable} in the Patients.
	 */
	public PatientForm(int id) {
		this.id=id;
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setBounds(100, 100, 685, 417);
		contentPane = new JPanel();
		mainFrame.add(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		lblName.setBounds(16, 24, 649, 40);
		contentPane.add(lblName);
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int deleteOK=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?");
				if(deleteOK==JOptionPane.OK_OPTION){
					db.deletePatient("patients", id);
					JOptionPane.showMessageDialog(null, "Patient deleted!");
					HomePage.updatePatients(db.Patients());
					mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
				} 
			}
		});
	
		btnDeletePatient.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnDeletePatient.setBounds(80, 325, 183, 48);
		contentPane.add(btnDeletePatient);
		
		lblDOB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter a valid date of birth:");
				if(in!=null){
					db.update("patients", "DOB", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblDOB.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblDOB.setBounds(16, 76, 428, 30);
		contentPane.add(lblDOB);
		
		lblAddress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter the new address:");
				if(in!=null){
					db.update("patients", "address", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblAddress.setBounds(16, 118, 428, 30);
		contentPane.add(lblAddress);
		
		lblPhone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter the new phone:");
				if(in!=null){
					db.update("patients", "phone", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPhone.setBounds(16, 160, 428, 30);
		contentPane.add(lblPhone);
		
		lblMedicalCondition.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame jf = new JFrame();
				String in=(String)JOptionPane.showInputDialog(jf, 
				        "Enter the new MC:",
				        "New MC",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        MedicalCondition.createNames(), 
				        MedicalCondition.createNames()[0]);
					
				if(in!=null){
					db.update("patients", "medicalCondition", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblMedicalCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblMedicalCondition.setBounds(16, 202, 428, 30);
		contentPane.add(lblMedicalCondition);
		
		/**
		 * The link is dynamically changed.
		 * @see profiles.MedicalCondition
		 */
		clickHere = new JLabel("<HTML><a href=\"\">Click Here!</a></HTML>");
		clickHere.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clickHere.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI(MedicalCondition.createWebsites(db.patientCard(id)[5])));
                    } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "No Link available!");
                    }
            }
        });
		clickHere.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		clickHere.setBounds(149, 235, 209, 30);
		contentPane.add(clickHere);
		
		lblComments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter the new comments:");
				if(in!=null){
					db.update("patients", "comments", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblComments.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblComments.setBounds(16, 296, 428, 30);
		contentPane.add(lblComments);
		
		lblBilling.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter the new billing:");
				if(in!=null){
					db.update("patients", "billing", id, in);
					updatePatientCard(id);
					HomePage.updatePatients(db.Patients());
				}
			}
		});
		lblBilling.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblBilling.setBounds(16, 265, 428, 30);
		contentPane.add(lblBilling);
		
		
		
		btnOtherPhotos.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnOtherPhotos.setBounds(470, 325, 183, 48);
		btnOtherPhotos.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				MorePhotos mp = new MorePhotos(id);
				mp.launchMorePhotos();
			}
		});
		contentPane.add(btnOtherPhotos);
		lblMainPhoto.setBounds(456, 40, 197, 273);
		
		contentPane.add(lblMainPhoto);
		
		String[] in = db.patientCard(id);
		lblName.setText("Name: "+in[0]+" "+in[1]);
		lblDOB.setText("Date of Birth: "+in[2]);
		lblAddress.setText("Address: "+in[3]);
		lblPhone.setText("Phone: "+in[4]);
		lblMedicalCondition.setText("Medical Condition: "+in[5]);
		lblComments.setText("Comments: "+in[7]);
		lblBilling.setText("Billing: "+in[6]);
		Image i=null;
		try {
			i = ImageIO.read(new File("img/"+in[8]));
		} catch (IOException e1) {
			try {
				i = ImageIO.read(new File("img/question-mark.jpg"));
			} catch (IOException e2) {
			
			}
			
		}
		ImageIcon ii= new ImageIcon(i.getScaledInstance(195, 200, Image.SCALE_DEFAULT));
		lblMainPhoto.setIcon(ii);
		lblMainPhoto.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
            	JFileChooser fc = new JFileChooser();
            	fc = new JFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
        		fc.setFileFilter(filter);
        		if (fc.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
        			try {
						Files.copy(Paths.get(fc.getSelectedFile().getAbsolutePath()),Paths.get("img/"+fc.getSelectedFile().getName()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			db.update("patients", "mainPhoto", id, fc.getSelectedFile().getName());
        			updatePatientCard(id);
        			} else {
					JOptionPane.showMessageDialog(null, "Select a file!");
				}
            }
        });
	}
	
	/**
	 * Loads the patient card's elements before showing the frame.
	 */
	public  void showPatientCard(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientForm frame = new PatientForm(id);
					frame.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Used to update the card after changes have been made.
	 * @param id patient ID of the card
	 */
	public void updatePatientCard(int id){
		String[] in = db.patientCard(id);
		lblName.setText("Name: "+in[0]+" "+in[1]);
		lblDOB.setText("Date of Birth: "+in[2]);
		lblAddress.setText("Address: "+in[3]);
		lblPhone.setText("Phone: "+in[4]);
		lblMedicalCondition.setText("Medical Condition: "+in[5]);
		lblComments.setText("Comments: "+in[7]);
		clickHere.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI(MedicalCondition.createWebsites(db.patientCard(id)[5])));
                    } catch (URISyntaxException | IOException ex) {
                    	System.out.println(ex);
                    }
            }
        });
		
		lblBilling.setText("Billing: "+in[6]);
		Image i=null;
		try {
			i = ImageIO.read(new File("img/"+in[8]));
		} catch (IOException e1) {
			
		}
		ImageIcon ii= new ImageIcon(i.getScaledInstance(195, 200, Image.SCALE_DEFAULT));
		
		lblMainPhoto.setIcon(ii);
	}
}
