package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dbcalls.DataBase;

import javax.swing.JPasswordField;
/**
 * The first frame that allows to login in the app.
 * @see HomePage
 * @author marco
 *
 */
public class LoginPage {

	public JFrame frame;
	public JLabel lblUsername;
	public JTextField textField;
	public JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public LoginPage() {
		frame = new JFrame();
		DataBase db = new DataBase();
		frame.setBounds(100, 100, 565, 388);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/**
		 * Checks the credentials with the {@code table} users.
		 */
		JButton btnEnter = new JButton("Enter!");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					// getPassword() returns a CharArray and I need to use the method equals to compare it with the input.
					if(java.util.Arrays.equals(db.Login(textField.getText()).toCharArray(),passwordField.getPassword())){
						HomePage hp = new HomePage();
						frame.setVisible(false);
						hp.launchHP();
					} else{
						// In this case the un was found but the pw is different.
						JOptionPane.showMessageDialog(null, "Wrong password!");
					}
				}catch(Exception err){
					JOptionPane.showMessageDialog(null, "Wrong credentials!");
				}
			}
		});
		btnEnter.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnEnter.setBounds(212, 271, 147, 49);
		frame.getContentPane().add(btnEnter);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblUsername.setBounds(151, 154, 94, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblPassword.setBounds(151, 205, 94, 16);
		frame.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		textField.setBounds(268, 149, 168, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Welcome to Sanus!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblNewLabel.setBounds(123, 43, 325, 59);
		frame.getContentPane().add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		passwordField.setBounds(268, 202, 168, 26);
		frame.getContentPane().add(passwordField);
	}
}
