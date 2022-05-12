package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.Course;
import Models.SqlUtils;
import Models.Student;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class StudentCard extends JFrame {

	private JPanel contentPane;
	private JTextField txtFldFirstName;
	private JLabel lblLastName;
	private JTextField txtFldLastName;
	private JLabel lblBirthDate;
	private JLabel lblAddress;
	private JTextField txtFldBirthDate;
	private JTextField txtFldAddress;
	private JSeparator separator;
	private JLabel lblCourses;
	private JScrollPane scrollPaneCourses;
	private JSeparator separator_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentCard frame = new StudentCard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public StudentCard() {
		
	}
	
	/**
	 * Create the frame.
	 */
	public StudentCard(Student student) {
		
		setTitle("Καρτέλα μαθητή");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0,screen.width,screen.height - 30);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStudentInfo = new JLabel("ΣΤΟΙΧΕΙΑ ΜΑΘΗΤΗ");
		lblStudentInfo.setFont(new Font("Arial", Font.PLAIN, 18));
		lblStudentInfo.setBounds(10, 10, 314, 37);
		contentPane.add(lblStudentInfo);
		
		JLabel lblFirstName = new JLabel("Όνομα");
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 12));
		lblFirstName.setBounds(30, 57, 126, 20);
		contentPane.add(lblFirstName);
		
		txtFldFirstName = new JTextField();
		txtFldFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFldFirstName.setBounds(166, 57, 158, 20);
		contentPane.add(txtFldFirstName);
		txtFldFirstName.setColumns(10);
		
		lblLastName = new JLabel("Επώνυμο");
		lblLastName.setFont(new Font("Arial", Font.BOLD, 12));
		lblLastName.setBounds(30, 87, 126, 20);
		contentPane.add(lblLastName);
		
		txtFldLastName = new JTextField();
		txtFldLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFldLastName.setBounds(166, 89, 158, 20);
		contentPane.add(txtFldLastName);
		txtFldLastName.setColumns(10);
		
		lblBirthDate = new JLabel("Ημερομηνία γέννησης");
		lblBirthDate.setFont(new Font("Arial", Font.BOLD, 12));
		lblBirthDate.setBounds(442, 58, 148, 20);
		contentPane.add(lblBirthDate);
		
		lblAddress = new JLabel("Διεύθυνση");
		lblAddress.setFont(new Font("Arial", Font.BOLD, 12));
		lblAddress.setBounds(442, 88, 148, 20);
		contentPane.add(lblAddress);
		
		txtFldBirthDate = new JTextField();
		txtFldBirthDate.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFldBirthDate.setBounds(649, 59, 183, 20);
		contentPane.add(txtFldBirthDate);
		txtFldBirthDate.setColumns(10);
		
		txtFldAddress = new JTextField();
		txtFldAddress.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFldAddress.setBounds(649, 89, 183, 20);
		contentPane.add(txtFldAddress);
		txtFldAddress.setColumns(10);
		
		separator = new JSeparator();
		separator.setBounds(10, 137, 1512, 2);
		contentPane.add(separator);
		
		/*
		 * lists of student's courses
		 */
		lblCourses = new JLabel("ΜΑΘΗΜΑΤΑ");
		lblCourses.setFont(new Font("Arial", Font.PLAIN, 18));
		lblCourses.setBounds(10, 149, 314, 37);
		contentPane.add(lblCourses);
		
		JLabel lblStudentsCourses = new JLabel("Δηλωμένα μαθήματα");
		lblStudentsCourses.setFont(new Font("Arial", Font.BOLD, 12));
		lblStudentsCourses.setBounds(30, 197, 126, 14);
		contentPane.add(lblStudentsCourses);
		
		JLabel lblOtherCourses = new JLabel("Υπόλοιπα μαθήματα");
		lblOtherCourses.setFont(new Font("Arial", Font.BOLD, 12));
		lblOtherCourses.setBounds(354, 197, 126, 14);
		contentPane.add(lblOtherCourses);
		
		scrollPaneCourses = new JScrollPane();
		scrollPaneCourses.setBounds(30, 212, 314, 250);
		contentPane.add(scrollPaneCourses);
		
		
		DefaultListModel coursesListModel = new DefaultListModel();
		JList coursesList = new JList(coursesListModel);
		coursesList.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneCourses.setViewportView(coursesList);
		
		JScrollPane scrollPaneAllCourses = new JScrollPane();
		scrollPaneAllCourses.setBounds(354, 212, 314, 250);
		contentPane.add(scrollPaneAllCourses);
		
		DefaultListModel allCoursesListModel = new DefaultListModel();
		JList allCoursesList = new JList(allCoursesListModel);
		allCoursesList.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneAllCourses.setViewportView(allCoursesList);
		ArrayList<Course> allCourses = new ArrayList<Course>();
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			courses = student.getCourses();
			allCourses = Course.getAll();
			coursesListModel.addAll(courses);
			allCoursesListModel.addAll(allCourses);
			for(Course element : allCourses) {
				for(Course element1 : courses) {
					if(element.getTitle().equals(element1.getTitle())) {
						allCoursesListModel.removeElement(element);
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnRemoveCourse = new JButton("Αφαίρεση μαθήματος");
		btnRemoveCourse.setBackground(new Color(255, 255, 255));
		btnRemoveCourse.setForeground(new Color(0, 102, 153));
		btnRemoveCourse.setFont(new Font("Arial", Font.BOLD, 12));
		btnRemoveCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = coursesList.getSelectedValue().toString();
				int course = coursesList.getSelectedIndex();
				allCoursesListModel.addElement(coursesListModel.remove(course));
				try {
					Course.removeCourse(title);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnRemoveCourse, "Σφάλμα");
				}
			}
		});
		btnRemoveCourse.setBounds(30, 472, 165, 21);
		contentPane.add(btnRemoveCourse);
		
		JButton btnAddCourse = new JButton("Δήλωση μαθήματος");
		btnAddCourse.setForeground(new Color(0, 102, 153));
		btnAddCourse.setFont(new Font("Arial", Font.BOLD, 12));
		btnAddCourse.setBackground(new Color(255, 255, 255));
		btnAddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = allCoursesList.getSelectedValue().toString();
				int course = allCoursesList.getSelectedIndex();
				coursesListModel.addElement(allCoursesListModel.remove(course));
				try {
					Course.addCourse(title, student);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnAddCourse, "Σφάλμα");
				}
			}
		});
		btnAddCourse.setBounds(354, 473, 165, 21);
		contentPane.add(btnAddCourse);
		
		/*
		 * student's personal information
		 */
		txtFldFirstName.setText(student.getFirstName());
		txtFldLastName.setText(student.getLastName());
		txtFldBirthDate.setText(student.getBirthDate());
		txtFldAddress.setText(student.getAddress());
		
		JButton btnUpdateStudentData = new JButton("Αποθήκευση");
		btnUpdateStudentData.setBackground(new Color(255, 255, 255));
		btnUpdateStudentData.setForeground(new Color(0, 102, 153));
		btnUpdateStudentData.setFont(new Font("Arial", Font.BOLD, 12));
		btnUpdateStudentData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student.setFirstName(txtFldFirstName.getText()); 
				student.setLastName(txtFldLastName.getText()); 
				student.setBirthDate(txtFldBirthDate.getText()); 
				student.setAddress(txtFldAddress.getText()); 
				try {
					student.update();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnUpdateStudentData, "Σφάλμα");
				}
			}
		});
		btnUpdateStudentData.setBounds(904, 73, 138, 21);
		contentPane.add(btnUpdateStudentData);
		
		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(692, 137, 2, 650);
		contentPane.add(separator_2);
		
		/*
		 * student's grades
		 */
		JLabel lblGrades = new JLabel("ΒΑΘΜΟΛΟΓΙΕΣ");
		lblGrades.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGrades.setBounds(704, 149, 314, 37);
		contentPane.add(lblGrades);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(704, 213, 680, 250);
		scrollPane.getViewport().setBackground(Color.WHITE);
		contentPane.add(scrollPane);
		
		JTable gradesTable = new JTable();
		scrollPane.setViewportView(gradesTable);
		
		DefaultTableModel gradesTableModel = new DefaultTableModel();
		String[] column = {"Τίτλος μαθήματος", "Βαθμοί"};
		String[] row = new String[2];
		gradesTableModel.setColumnIdentifiers(column);
		gradesTable.setModel(gradesTableModel);
		
		/*
		 * save student's grade
		 */
		gradesTableModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE) {
					 int row = e.getFirstRow();
					 int column = e.getColumn();
					 
					 String grade = String.valueOf(gradesTableModel.getValueAt(row, column));
					 String course = String.valueOf(gradesTableModel.getValueAt(row, 0));
					 try {
						student.updateGrade(course, grade);
					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(gradesTable, "Σφάλμα");
					}
				}
			}
		});
		if(gradesTable.getCellEditor() != null) {
			gradesTable.getCellEditor().stopCellEditing();
		}

		for(Course c : courses) {
			row[0] = c.getTitle();
			try {
				if(c.getGrade(student) != 0) {
					row[1] = String.valueOf(c.getGrade(student));
				} else {
					row[1] = "Δεν έχει βαθμολογηθεί";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gradesTableModel.addRow(row);
		}
	}
}
