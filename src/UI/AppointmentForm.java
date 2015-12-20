package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbcalls.DataBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
/**
 * Creates the frame with all the data for a specific appointment.
 * @author marco
 * @see Appointments
 */
public class AppointmentForm  {

	private JPanel contentPane;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblComments;
	private JFrame jframeA;
	int appointmentId;
	DataBase db = new DataBase();


	/**
	 * Launch the application.
	 */
	public  void showAppointmentCard() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppointmentForm frame = new AppointmentForm(appointmentId);
					frame.jframeA.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This {@code JFrame} contains all we need for the Appointment form. We can access and edit all the details. 
	 * @param appointmentId the appointment ID that we want to get more info about.
	 */
	public AppointmentForm(int appointmentId) {
		this.appointmentId = appointmentId;
		jframeA = new JFrame();
		String[] results =db.appointmentCard(appointmentId);
		
		jframeA.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jframeA.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPatient = new JLabel("Patient: "+results[0]+" "+results[1]);
		lblPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatient.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblPatient.setBounds(51, 20, 363, 49);
		contentPane.add(lblPatient);
		
		lblTime = new JLabel("Time: "+results[2]);
		lblTime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter a new time:");
				if(in!=null){
					db.update("appointments", "time", appointmentId, in);
					updateAppointmentCard(appointmentId);
					HomePage.updateAppointments(db.Appointments());
				}
			}
		});
		lblTime.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblTime.setBounds(133, 81, 204, 38);
		contentPane.add(lblTime);
		
		lblDate = new JLabel("Date: "+results[3]);
		lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblDate.setBounds(133, 119, 204, 38);
		lblDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter a new date:");
				if(in!=null){
					db.update("appointments", "date", appointmentId, in);
					updateAppointmentCard(appointmentId);
					HomePage.updateAppointments(db.Appointments());
				}
			}
		});
		contentPane.add(lblDate);
		
		lblComments = new JLabel("Comments: "+results[4]);
		lblComments.setHorizontalAlignment(SwingConstants.CENTER);
		lblComments.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblComments.setBounds(41, 179, 382, 38);
		lblComments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in=JOptionPane.showInputDialog("Enter a new comment:");
				if(in!=null){
					db.update("appointments", "comments", appointmentId, in);
					updateAppointmentCard(appointmentId);
					HomePage.updateAppointments(db.Appointments());
				}
			}
		});
		contentPane.add(lblComments);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnDelete.setBounds(150, 229, 150, 43);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int deleteOK=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?");
				if(deleteOK==JOptionPane.OK_OPTION){
					db.deletePatient("appointments", appointmentId);
					JOptionPane.showMessageDialog(null, "appointment deleted!");
					HomePage.updateAppointments(db.Appointments());
					jframeA.setVisible(false);
				} 
			}
		});
		contentPane.add(btnDelete);
	}
	/**
	 * If changes are made to an appointment this is called to load again all the information from the database.
	 * @see dbcalls.DataBase
	 * @param appointmentId the appointment ID
	 */
	public void updateAppointmentCard(int appointmentId){
		String[] in = db.appointmentCard(appointmentId);
		try{
			lblComments.setText("Comments: "+in[4]);
			lblTime.setText("Time: "+in[2]);
			lblDate.setText("Date: "+in[3]);
		} catch(Exception e){
			System.out.println(e);
		}
	}
}
