package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dbcalls.DataBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.SwingConstants;
/**
 * To access three more photos.
 * @author marco
 * @see AppointmentForm
 */
public class MorePhotos{
	static Image i1;
	static Image i2;
	static Image i3;
	static JFrame jf = new JFrame();
	private JPanel contentPane;
	static int id;
	DataBase db = new DataBase();


	/**
	 * Launch the application.
	 */
	public static void launchMorePhotos() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MorePhotos frame = new MorePhotos(id);
					jf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * The user can enter a total of three additional pictures. If the picture is not there, a question mark should appear.
	 * The user can click on existing pictures to replace them. Only pictures can be accepted as file.
	 * @param id the patient ID
	 */
	public MorePhotos(int id) {
		this.id = id;
		jf.setBounds(100, 100, 782, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jf.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] patientInfo=db.patientCard(id);
		
		JLabel lblNewLabel = new JLabel("More Photos of "+patientInfo[0]+" "+patientInfo[1]);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setBounds(178, 30, 426, 40);
		contentPane.add(lblNewLabel);
		
		// pic 1
		JLabel pan1 = new JLabel();
		pan1.setBounds(36, 110, 213, 215);
		i1=null;
		try {
			if(patientInfo[9]==null||patientInfo[9].length()<2||patientInfo[9]==""){
				i1=ImageIO.read(new File("img/question-mark.jpg"));
			}else{
				i1 = ImageIO.read(new File("img/"+patientInfo[9]));
				
			}
		} catch (IOException e1) {
			System.out.println(e1);
		}
		ImageIcon ii1= new ImageIcon(i1.getScaledInstance(195, 200, Image.SCALE_DEFAULT));
		pan1.setIcon(ii1);
		pan1.addMouseListener(new MouseAdapter()
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
						e.printStackTrace();
					}
        			db.update("patients", "photo1", id, fc.getSelectedFile().getName());
        			try {
						i1 = ImageIO.read(new File("img/"+fc.getSelectedFile().getName()));
						
						MorePhotos mp = new MorePhotos(id);
						mp.launchMorePhotos();
					} catch (IOException e) {
						System.out.println(e);
					}
        			} else {
					JOptionPane.showMessageDialog(null, "Select a file!");
				}
            }
        });
		contentPane.add(pan1);
		
		// pic 2
		JLabel pan2 = new JLabel();
		pan2.setBounds(280, 110, 213, 215);
		i2=null;
		try {
			if(patientInfo[10]==null||patientInfo[10].length()<2||patientInfo[10]==""){
				i2=ImageIO.read(new File("img/question-mark.jpg"));
			}else{
				i2 = ImageIO.read(new File("img/"+patientInfo[10]));
			}
		} catch (IOException e1) {
			System.out.println(e1);
		}
		ImageIcon ii2= new ImageIcon(i2.getScaledInstance(195, 200, Image.SCALE_DEFAULT));
		pan2.setIcon(ii2);
		pan2.addMouseListener(new MouseAdapter()
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
						e.printStackTrace();
					}
        			db.update("patients", "photo2", id, fc.getSelectedFile().getName());
        			try {
						i1 = ImageIO.read(new File("img/"+fc.getSelectedFile().getName()));
						
						MorePhotos mp = new MorePhotos(id);
						mp.launchMorePhotos();
					} catch (IOException e) {
						System.out.println(e);					
					}
        		} else {
					JOptionPane.showMessageDialog(null, "Select a file!");
				}
            }
        });
		contentPane.add(pan2);
		
		// third pic
		JLabel pan3 = new JLabel();
		pan3.setBounds(519, 110, 213, 215);
		i3=null;
		try {
			if(patientInfo[11]==null||patientInfo[11].length()<2||patientInfo[11]==""){
				i3=ImageIO.read(new File("img/question-mark.jpg"));
			}else{
				i3 = ImageIO.read(new File("img/"+patientInfo[11]));
			}
		} catch (IOException e1) {
			System.out.println(e1);
		}
		ImageIcon ii3= new ImageIcon(i3.getScaledInstance(195, 200, Image.SCALE_DEFAULT));
		pan3.setIcon(ii3);
		pan3.addMouseListener(new MouseAdapter()
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
						e.printStackTrace();
					}
        			db.update("patients", "photo3", id, fc.getSelectedFile().getName());
        			try {
						i3 = ImageIO.read(new File("img/"+fc.getSelectedFile().getName()));
						
						MorePhotos mp = new MorePhotos(id);
						mp.launchMorePhotos();
					} catch (IOException e) {
						System.out.println(e);
					}
        			} else {
					JOptionPane.showMessageDialog(null, "Select a file!");
				}
            }
        });
		contentPane.add(pan3);
	}


}
