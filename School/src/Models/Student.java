package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Student {

	private int ID;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private String address;
	
	public Student(String firstName, String lastName, String gender, String birthDate, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	/*
	 * inserts a new student in the db
	 */
	public void register() throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		statement.execute("INSERT INTO students (firstName, lastName, gender, birthDate, address) VALUES ('"+this.firstName+"', '"+this.lastName+"', '"+this.gender+"', '"+this.birthDate+"', '"+this.address+"')"); 
	}
	
	public void update() throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		statement.execute("UPDATE students SET firstName='"+this.firstName+"', lastName='"+this.lastName+"', birthDate='"+this.birthDate+"', address='"+this.address+"' WHERE id='"+this.ID+"'");
	}
	
	/*
	 * this method searches in the db with student's lastname
	 * if the resultSet in not empty, for every result an new Student object is created
	 * returns an array list with the students
	 */
	public static ArrayList<Student> search(String searchText) throws SQLException{
		ArrayList<Student> students = new ArrayList<Student>();
		
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE lastName='"+searchText+"'");
		if(resultSet.next()) {
			resultSet.first();
			while(!resultSet.isAfterLast()) {
				Student student = new Student(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("birthDate"), resultSet.getString("address"));
				student.setID(resultSet.getInt("id"));
				students.add(student);
				resultSet.next();
				
			}
		}
		return students;
	}
	
	
	/*
	 * this method returns an array list with all the courses a student has
	 * finds courses with student's id
	 */
	public ArrayList<Course> getCourses() throws SQLException {
		ArrayList<Course> courses = new ArrayList<Course>();
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM `studentcourse` WHERE studentId = '"+this.ID+"'");
		
		if(resultSet.next()) {
			resultSet.first();
			while(!resultSet.isAfterLast()) {
				Course course = new Course(resultSet.getInt("CourseId"));
				course.setID(resultSet.getInt("CourseId"));
				courses.add(course);
				resultSet.next();
			}
		}
		return courses;
	}
	
	public void updateGrade(String course, String grade) throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE title ='" + course + "'"); 
		
		if(resultSet.next()) {
			resultSet.first();
			int courseId = resultSet.getInt("id");
			statement.execute("UPDATE studentcourse SET grade='" + Integer.valueOf(grade) + "' WHERE studentId='" + this.ID + "' and CourseId='"+ courseId +"'");
		}
		
		
	}
}
