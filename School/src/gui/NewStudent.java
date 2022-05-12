package gui;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Models.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewStudent extends JFrame {

	private JPanel contentPane;
	private JTextField txtFldFirstName;
	private JTextField txtFldLastName;
	private JTextField txtFldBirthDate;
	private JTextField txtFldAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewStudent frame = new NewStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewStudent() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setTitle("Εγγραφή μαθητή");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(screen.width/2-300, screen.height/2-300, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("Όνομα");
		lblFirstName.setBounds(100, 95, 150, 30);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Επώνυμο");
		lblLastName.setBounds(100, 155, 150, 30);
		contentPane.add(lblLastName);
		
		JLabel lblGender = new JLabel("Φύλο");
		lblGender.setBounds(100, 215, 150, 30);
		contentPane.add(lblGender);
		
		JLabel lblBirthDate = new JLabel("Ημερομηνία γέννησης");
		lblBirthDate.setBounds(100, 275, 150, 30);
		contentPane.add(lblBirthDate);
		
		JLabel lblAddress = new JLabel("Διεύθυνση κατοικίας");
		lblAddress.setBounds(100, 335, 150, 30);
		contentPane.add(lblAddress);
		
		txtFldFirstName = new JTextField();
		txtFldFirstName.setBounds(300, 95, 150, 30);
		contentPane.add(txtFldFirstName);
		txtFldFirstName.setColumns(10);
		
		txtFldLastName = new JTextField();
		txtFldLastName.setBounds(300, 155, 150, 30);
		contentPane.add(txtFldLastName);
		txtFldLastName.setColumns(10);
		
		JRadioButton rdBtnMale = new JRadioButton("Αγόρι");
		rdBtnMale.setBounds(290, 215, 103, 21);
		contentPane.add(rdBtnMale);
		rdBtnMale.setActionCommand("Male");
		
		JRadioButton rdBtnFemale = new JRadioButton("Κορίτσι");
		rdBtnFemale.setBounds(395, 215, 103, 21);
		contentPane.add(rdBtnFemale);
		rdBtnFemale.setActionCommand("Female");
		
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(rdBtnMale);
		genderGroup.add(rdBtnFemale);
		
		txtFldBirthDate = new JTextField();
		txtFldBirthDate.setBounds(300, 275, 150, 30);
		contentPane.add(txtFldBirthDate);
		
		txtFldAddress = new JTextField();
		txtFldAddress.setBounds(300, 335, 150, 30);
		contentPane.add(txtFldAddress);
		txtFldAddress.setColumns(10);
		
		JButton btnRegisterStudent = new JButton("Εγγραφή");
		btnRegisterStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = txtFldFirstName.getText();
				String lname = txtFldLastName.getText();
				String gender = genderGroup.getSelection().getActionCommand();
				String birthDate = txtFldBirthDate.getText() ;
				String address = txtFldAddress.getText();
				Student student = new Student(fname, lname, gender, birthDate, address);
				try {
					student.register();
					JOptionPane.showMessageDialog(btnRegisterStudent, "Επιτυχής εγγραφή!");
					dispose();
				}catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnRegisterStudent, "Σφάλμα στην εγγραφή");
				}
			}
		});
		btnRegisterStudent.setBounds(240, 435, 120, 30);
		contentPane.add(btnRegisterStudent);
	}
}
