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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Creates the form to find selected appointments.
 * @author marco
 * @see Appointments
 */
public class SearchAppointment extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	DataBase db = new DataBase();


	/**
	 * Launch the application.
	 */
	public static void launchSearchAppointments() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchAppointment frame = new SearchAppointment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. It will essentially make a call to dbcalls.DataBase.searchAppointments(keyword) which has multiple {@code UNION}s.
	 */
	public SearchAppointment() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterWhatYou = new JLabel("Find an appointment!");
		lblEnterWhatYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterWhatYou.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblEnterWhatYou.setBounds(57, 25, 365, 57);
		contentPane.add(lblEnterWhatYou);
		
		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		textField.setBounds(36, 142, 386, 41);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search!");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(db.searchAppointments(textField.getText()).size()==0)
				{
					
					JOptionPane.showMessageDialog(null, "Nothing found!");
				} else
				{
					HomePage.updateAppointments(db.searchAppointments(textField.getText()));
					setVisible(false);
				}
			}
		});
		btnSearch.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnSearch.setBounds(155, 195, 132, 53);
		contentPane.add(btnSearch);
		
		JLabel lblYouCanEnter = new JLabel("You can enter a last name, date or time of appointment.");
		lblYouCanEnter.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouCanEnter.setBounds(6, 83, 438, 21);
		contentPane.add(lblYouCanEnter);
	}
}