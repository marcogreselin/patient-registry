package dbcalls;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.MainClass;
/**
 * All calls to the SQLite database are contained in this class.
 * @author marco
 *
 */
public class DataBase {
	public static Connection connection=null;
	File absPath = new File("");
	/**
	 * Used in the Login page to check credentials against the records in the table {@code users}.
	 * See <a href="http://www.tutorialspoint.com/sqlite/sqlite_java.htm">Tutorial Sprint</a>.
	 * @param un inputted by the user.
	 * @return the password associate with the parsed username.
	 */
	public  String Login(String un){
	    Statement statement = null;
	    try {
	    	System.out.println("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
	      Class.forName("org.sqlite.JDBC");
	      connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
	      connection.setAutoCommit(false);
	      String password=null;
	      statement = connection.createStatement();
	      ResultSet rs = statement.executeQuery( "SELECT password, fullName FROM users WHERE username = '"+un+"';" );
	      while ( rs.next() ) {
	         password = rs.getString("password");
	         MainClass.userFullName = rs.getString("fullName");
	      }
	      rs.close();
	      statement.close();
	      connection.close();
	      return password;
	    } catch ( Exception e ) {
	      System.out.println(e);
	      return null;
	    }
	}
	
	/**
	 * Used to return the full list of patients. Used in {@link UI.Patients}.
	 * @return the {@code ArrayList<String[]>} used in the {@code JTable}.
	 */
	public  ArrayList<String[]> Patients(){
	    Statement statement = null;
	    try {
	    	ArrayList<String[]> patientsRows = new ArrayList<String[]>(); 
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( "SELECT firstName, lastName, DOB, id FROM patients;" );
			while ( rs.next() ) {
				patientsRows.add( new String[]{
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("DOB"),
						rs.getString("id")});
			}
			rs.close();
			statement.close();
			connection.close();
			return patientsRows;
		} catch ( Exception e ) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Used to display the Patient card
	 * @param id patient id
	 * @return the JFrame
	 * @see UI.PatientForm used by this one 
	 */
	public  String[] patientCard(int id){
	    Statement statement = null;
	    try {
	    	String[] out = new String[12]; 
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( "SELECT firstName, lastName, DOB, address, phone, medicalCondition, billing, comments, mainPhoto, photo1, photo2, photo3 FROM patients WHERE id ="+id+";" );
			out[0]=rs.getString("firstName");
			out[1]=rs.getString("lastName");
			out[2]=rs.getString("DOB");
			out[3]=rs.getString("address");
			out[4]=rs.getString("phone");
			out[5]=rs.getString("medicalCondition");
			out[6]=rs.getString("billing");
			out[7]=rs.getString("comments");
			out[8]=rs.getString("mainPhoto");
			out[9]=rs.getString("photo1");
			out[10]=rs.getString("photo2");
			out[11]=rs.getString("photo3");
			rs.close();
			statement.close();
			connection.close();
			return out;
		} catch ( Exception e ) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Used in many places to update a table in the database after changes have been ordered in the UI.
	 * @param table which table in the database
	 * @param column which column
	 * @param id which id (Patient or Appointment ID)
	 * @param input what to replace with
	 */
	public  void update(String table, String column, int id, String input){
	    Statement statement = null;
	    try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			statement = connection.createStatement();
			statement.executeUpdate( "UPDATE "+table+" SET "+column+"='"+input+"' WHERE id="+id+";" );
			statement.close();
			connection.close();
		} catch ( Exception e ) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * Deletes a patient.
	 * @param table name of the table 
	 * @param id user to delete
	 */
	public  void deletePatient(String table, int id){
	    Statement statement = null;
	    try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			statement = connection.createStatement();
			statement.executeUpdate( "DELETE FROM "+table+" WHERE id="+id+";" );
			statement.close();
			connection.close();
		} catch ( Exception e ) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * Used to return the full list of patients. Used in {@link UI.Appointments}.
	 * @return the {@code ArrayList<String[]>} used in the {@code JTable}.
	 */
	public  ArrayList<String[]> Appointments(){
	    Statement statement = null;
	    try {
	    	ArrayList<String[]> patientsRows = new ArrayList<String[]>(); 
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( "SELECT lastName, date, time, appointments.id FROM patients JOIN appointments ON patients.id=appointments.patientId;" );
			while ( rs.next() ) {
				patientsRows.add( new String[]{
				rs.getString("lastName"),
				rs.getString("date"),
				rs.getString("time"),
				rs.getString("id")});
			}
			rs.close();
			statement.close();
			connection.close();
			return patientsRows;
		} catch ( Exception e ) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param input the whole query
	 * @return {@code true} if everything went ok. Otherwise...
	 */
	public  boolean AddPatient(String input){
		Statement statement = null;
	    try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate(input);
			statement.close();
			connection.commit();
			connection.close();
			JOptionPane.showMessageDialog(null, "Added!");
			return true;
	    } catch ( Exception e ) {
			JOptionPane.showMessageDialog(null, "Error adding the patient");
	    	System.out.println(e);
	    	return false;
	    }
	}
	
	/**
	 * Used to display the Appointment card
	 * @param id appointment id
	 * @return the array that will feed the appointment card
	 * @see UI.AppointmentForm
	 */
	public  String[] appointmentCard(int id){
	    Statement statement = null;
	    try {
	    	String[] out = new String[5]; 
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( "SELECT firstName, lastName, time, date, 'appointments'.comments FROM appointments"
					+ " JOIN patients ON patients.id=appointments.patientId WHERE appointments.id ="+id+";" );
			out[0]=rs.getString("firstName");
			out[1]=rs.getString("lastName");
			out[2]=rs.getString("time");
			out[3]=rs.getString("date");
			out[4]=rs.getString("comments");
			rs.close();
			statement.close();
			connection.close();
			return out;
		} catch ( Exception e ) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Used to find patients using a keyword. The query will look for the keyword in all the columns.
	 * @param keyword used to search
	 * @return the list that we will use to feed the table.
	 * @see UI.SearchPatient
	 */
	public  ArrayList<String[]> searchPatients(String keyword){
	    Statement statement = null;
	    try {
	    	ArrayList<String[]> patientsRows = new ArrayList<String[]>(); 
	    	Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( "SELECT firstName, lastName, DOB, id FROM patients WHERE firstName LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE lastName LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE DOB LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE address LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE phone LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE medicalCondition LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE billing LIKE '%"+keyword+"%' UNION  "
			+"SELECT firstName, lastName, DOB, id FROM patients WHERE comments LIKE '%"+keyword+"%' " );
			while ( rs.next() ) {
				patientsRows.add( new String[]{
				rs.getString("firstName"),
				rs.getString("lastName"),
				rs.getString("DOB"),
				rs.getString("id")});
			}
			rs.close();
			statement.close();
			connection.close();
			return patientsRows;
		} catch ( Exception e ) {
			System.out.println( e);
			return null;
		}
	}
	
	/**
	 * Used to find appointments using a keyword. The query will look for the keyword in all the columns.
	 * @param keyword used to search
	 * @return the list that we will use to feed the table.
	 * @see UI.SearchAppointment
	 */
	public  ArrayList<String[]> searchAppointments(String keyword){
	    Statement statement = null;
	    try {
	    	ArrayList<String[]> patientsRows = new ArrayList<String[]>(); 
	    	Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT lastName, date, time, appointments.id AS id FROM patients JOIN appointments ON patients.id=appointments.patientId WHERE lastName LIKE '%"+keyword+"%' UNION  "+
					"SELECT lastName, date, time, appointments.id AS id FROM patients JOIN appointments ON patients.id=appointments.patientId WHERE date LIKE '%"+keyword+"%' UNION  " +
					"SELECT lastName, date, time, appointments.id AS id FROM patients JOIN appointments ON patients.id=appointments.patientId WHERE time LIKE '%"+keyword+"%';");
			while ( rs.next() ) {
				patientsRows.add( new String[]{
				rs.getString("lastName"),
				rs.getString("date"),
				rs.getString("time"),
				rs.getString("id")});
			}
			rs.close();
			statement.close();
				connection.close();
			return patientsRows;
		} catch ( Exception e ) {
			System.out.println( e);
			return null;
		}
	}
	
	/**
	 * Used in the export panel to export all patients.
	 * @return the data to export
	 * @see UI.ImportExport
	 */
	public  ArrayList<String[]> SelectAllPatients(){
		Statement statement = null;
	    try {
	    	ArrayList<String[]> patientsRows = new ArrayList<String[]>(); 
	    	Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite"+absPath+File.pathSeparator+"PatientRegistry.sqlite");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM patients");
			while ( rs.next() ) {
				patientsRows.add( new String[]{
					rs.getString("id"),
					rs.getString("firstName"),
					rs.getString("lastName"),
					rs.getString("DOB"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getString("medicalCondition"),
					rs.getString("billing"),
					rs.getString("comments"),
					rs.getString("mainPhoto"),
					rs.getString("photo1"),
					rs.getString("photo2"),
					rs.getString("photo3")});
			}
			rs.close();
			statement.close();
				connection.close();
			return patientsRows;
		} catch ( Exception e ) {
			System.out.println( e);
			return null;
		}
		
	}
}
