package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Models.Course;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class NewCourse extends JFrame {

	private JPanel contentPane;
	private JTextField txtFldCourseTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewCourse frame = new NewCourse();
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
	public NewCourse() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setTitle("Νέο μάθημα");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(screen.width/2-275, screen.height/2-130, 550, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCourseTitle = new JLabel("Τίτλος μαθήματος");
		lblCourseTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCourseTitle.setBounds(100, 60, 130, 30);
		contentPane.add(lblCourseTitle);
		
		txtFldCourseTitle = new JTextField();
		txtFldCourseTitle.setBounds(250, 60, 250, 30);
		contentPane.add(txtFldCourseTitle);
		txtFldCourseTitle.setColumns(10);
		
		JButton btnAddNewCourse = new JButton("Καταχώρηση");
		btnAddNewCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = txtFldCourseTitle.getText();
				Course course = new Course(title);
				try {
					course.save();
					JOptionPane.showMessageDialog(btnAddNewCourse, "Επιτυχής καταχώρηση μαθήματος!");
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnAddNewCourse, "Σφάλμα στην καταχώρηση");
				}
			}
		});
		btnAddNewCourse.setBounds(205, 145, 140, 20);
		contentPane.add(btnAddNewCourse);
	}
}
