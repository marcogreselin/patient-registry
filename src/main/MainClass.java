package main;

import java.awt.EventQueue;
import UI.LoginPage;

/**
 * 'Sanus' is a Patient Registry developed as part of 
 * UCL course COMPGC01 as a main Coursework.
 * It features a number of implementations including an
 * SQL database for the management of the users and the patients'
 * records. 
 * @author marco
 */
public class MainClass {
	
	public static String userFullName;
	
	/**
	 * Launch the application.
	 * @param args don't touch
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
