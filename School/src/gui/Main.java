package gui;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Models.Student;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class Main {

	private JFrame frmSchool;
	private JTextField txtFldSearchStudent;
	
	ArrayList<Student> students; // used on the table

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmSchool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSchool = new JFrame();
		frmSchool.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frmSchool.getContentPane().setBackground(new Color(204, 204, 255));
		frmSchool.setTitle("School");
		// for full screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frmSchool.setBounds(0, 0,screen.width,screen.height - 30);
		frmSchool.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frmSchool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSchool.getContentPane().setLayout(null);
		
		JButton btnNewStudent = new JButton("Νέος μαθητής");
		btnNewStudent.setForeground(new Color(0, 102, 153));
		btnNewStudent.setBackground(new Color(255, 255, 255));
		btnNewStudent.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnNewStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewStudent newStudent = new NewStudent();
				newStudent.setVisible(true);
			}
		});
		btnNewStudent.setBounds(screen.width/2-210, screen.height/2+200, 160, 30);
		frmSchool.getContentPane().add(btnNewStudent);
		
		JButton btnNewCource = new JButton("Νέο μάθημα");
		btnNewCource.setForeground(new Color(0, 102, 153));
		btnNewCource.setBackground(new Color(255, 255, 255));
		btnNewCource.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnNewCource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewCourse newCourse = new NewCourse();
				newCourse.setVisible(true);
			}
		});
		btnNewCource.setBounds(screen.width/2+50, screen.height/2+200, 160, 30);
		frmSchool.getContentPane().add(btnNewCource);
		
		JLabel lblSearchStudent = new JLabel("Αναζήτηση μαθητή");
		lblSearchStudent.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblSearchStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchStudent.setBounds(screen.width/2+130, screen.height/2-300, 185, 40);
		frmSchool.getContentPane().add(lblSearchStudent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(screen.width/2-500, screen.height/2-250, 1000, 400);
		scrollPane.getViewport().setBackground(Color.WHITE);
		frmSchool.getContentPane().add(scrollPane);
		
		JTable studentTable = new JTable();
		studentTable.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		studentTable.setBackground(new Color(255, 255, 255));
		studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ //Select row in mouse click
	        public void valueChanged(ListSelectionEvent event) {
	        	if(event.getValueIsAdjusting()) {
	        		/* get row's index
	        		 * find the student on this index in the array list 'students'
	        		 * open this students's card
	        		 */
	        		int index = studentTable.getSelectedRow();
	        		Student student = students.get(index);
	        		StudentCard studentCard = new StudentCard(student);
		        	studentCard.setVisible(true);
	        	}
	        }
	    });
		
		DefaultTableModel tableModel = new DefaultTableModel();
		String[] column = {"ΑΜ", "Όνομα", "Επώνυμο", "Φύλο", "Ημερομηνία γέννησης", "Διεύθυνση"}; //table column names
		String[] row = new String[6];
		tableModel.setColumnIdentifiers(column);
		studentTable.setModel(tableModel);
		scrollPane.setViewportView(studentTable);
				
		txtFldSearchStudent = new JTextField();
		txtFldSearchStudent.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblSearchStudent.setLabelFor(txtFldSearchStudent);
		txtFldSearchStudent.setBounds(screen.width/2+315, screen.height/2-300, 185, 40);
		frmSchool.getContentPane().add(txtFldSearchStudent);
		txtFldSearchStudent.setColumns(10);
		
		txtFldSearchStudent.addKeyListener(new KeyAdapter() {
			
			/* with ENTER
			 * search in the db and return an array list with all the results
			 * for every element of this list a row is added to the table
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					while(tableModel.getRowCount() != 0) {
						tableModel.removeRow(0);
					}
					String searchText = txtFldSearchStudent.getText();
					try {
						students = Student.search(searchText);
						if(students.size() == 0) {
							JOptionPane.showMessageDialog(studentTable, "Δεν βέθηκαν αποτελέσματα");
						}
						for(Student element : students) {
							row[0] = String.valueOf(element.getID());
							row[1] = element.getFirstName();
							row[2] = element.getLastName();
							row[3] = element.getGender();
							row[4] = element.getBirthDate();
							row[5] = element.getAddress();
							tableModel.addRow(row);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(studentTable, "Σφάλμα στην επικοινωνία με τον server.");
					}
				}
			}
		});
		
		
	}
}
