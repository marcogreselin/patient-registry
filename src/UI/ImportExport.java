package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import dbcalls.DataBase;
import main.MainClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * The third {@code JPanel} is used to attach a couple of buttons to the main frame.
 * The class also contains call to the database to retrieve/store data.
 * <b>All {@code .csv} here are meant to be comma separated</b>
 * @see HomePage
 */
public class ImportExport {
	JPanel IEPanel;
	DataBase db = new DataBase();


	public JPanel launchImportExport(){
		IEPanel = new JPanel();
		IEPanel.setBounds(6, 152, 882, 417);
		
		IEPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Import CSV");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
            	fc = new JFileChooser();
            	FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
        		fc.setFileFilter(filter);
            	if (fc.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
            		try {
						BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile().getAbsolutePath()));
						String line;
						while ( (line=br.readLine()) != null)
						{
					         String[] values = line.split(",");
					         db.AddPatient("INSERT INTO patients (firstName, lastName, DOB, address, phone, medicalCondition, billing, Comments) VALUES('"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+values[7]+"');");
						}
					} catch (Exception e1) {
						System.out.println(e1);
					}
            	}
			}
		});
		btnNewButton.setBounds(146, 80, 241, 236);
		IEPanel.add(btnNewButton);
		
		JButton btnExportCsv = new JButton("Export CSV");
		btnExportCsv.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		btnExportCsv.setBounds(516, 80, 241, 236);
		btnExportCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
            	fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			    fc.showSaveDialog(null);
			    try {
			    	// don't double-click on the directory!
			    	File f = new File(fc.getSelectedFile().getAbsolutePath()+"/results.csv");
			    	f.getParentFile().mkdirs(); 
			    	f.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					
					ArrayList<String[]> results = db.SelectAllPatients();
					// no comma should be put on the last element of a line.
					for ( int i =0; i<results.size();i++)
					{
						for(int s=0;s<results.get(i).length;s++){
							
							if(s<results.get(i).length-1)
								bw.write(results.get(i)[s]+",");  
							else
								bw.write(String.valueOf(results.get(i)[s]));
						}
						bw.newLine(); 
					}
					bw.close();
					JOptionPane.showMessageDialog(null, "Export completed");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Export failed");
				}    
			}
		});
		IEPanel.add(btnExportCsv);
		
		return IEPanel;
	}
}