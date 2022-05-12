package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
	private int ID;
	private String title;
	private ArrayList<Student> students = new ArrayList<Student>();
	
	
	
	public Course(String title) {
		super();
		this.title = title;
	}
	
	public Course(int id) throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM courses where id='"+id+"'");

		resultSet.first();
		this.title = resultSet.getString("title");
	}
	
	@Override
	public String toString() {
		return title;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public static ArrayList<Course> getAll() throws SQLException{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM courses");
		resultSet.first();
		
		while(!resultSet.isAfterLast()) {
			Course course = new Course(resultSet.getString("title"));
			course.setID(resultSet.getInt("id"));
			courses.add(course);
			resultSet.next();
		}
		return courses;
	}
	
	public void save() throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		statement.execute("INSERT INTO courses (title) VALUES ('"+this.title+"')");
	}
	
	public int getGrade(Student student) throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM studentcourse WHERE studentId = '"+student.getID()+"' AND courseId = '"+this.getID()+"'");
		if(resultSet.next()) {
			resultSet.first();
			return resultSet.getInt("grade");
		} else {
			return 0;
		}
		
	}
	
	public static void addCourse(String title, Student student) throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE title='"+title+"'");
		resultSet.first();
		
		statement.execute("INSERT INTO studentcourse (studentId, CourseId) VALUES ('"+student.getID()+"','"+resultSet.getInt("id")+"' )");
		
	}
	
	public static void removeCourse(String title) throws SQLException {
		Connection connection = SqlUtils.getConnection();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE title='"+title+"'");
		resultSet.first();
		
		statement.execute("DELETE FROM studentcourse WHERE CourseId='"+resultSet.getInt("id")+"'");
	}
}
