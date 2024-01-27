package com.codtechitsolutions.studentgrademanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// This class is used to perform database operations
public class StudentDatabase {
    
    private Connection connection;
    
    // Used for connecting with the database
    public StudentDatabase() {
	try {
	    String url = "jdbc:mysql://localhost:3306/testdb";
	    String username = "root";
	    String password = "root";
	    
	    connection = DriverManager.getConnection(url, username, password);
	    createTable();
	} catch(SQLException e) {
	    e.printStackTrace();
	}
    }
    
    // Used to create new table if table does not exist in database
    private void createTable() {
	try(Statement statement = connection.createStatement()) {
	    String createSQLTable = "CREATE TABLE IF NOT EXISTS students "+
		    			"(id INT AUTO_INCREMENT PRIMARY KEY,"+
		    			"name VARCHAR(50),"+
		    			"roll_no INT,"+
		    			"marks TEXT)";
	    statement.executeUpdate(createSQLTable);
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	
    }
    
    // Used to add new student record to the database
    public int addStudent(Student student) {
	try {
	    String query = "INSERT INTO students (name, roll_no, marks) VALUES (?,?,?)";
	    PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, student.getName());
	    ps.setInt(2, student.getRollNo());
	    ps.setString(3, marksListToString(student.getSubjectMarks()));
	    
	    ps.executeUpdate();
	    
	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    if(generatedKeys.next()) {
		return generatedKeys.getInt(1);
	    }
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return -1;
    }

 // Used to convert marks list to string as it is stored as string/text in database
    private String marksListToString(List<Integer> subjectMarks) {
	StringBuilder marksString = new StringBuilder();
	for(Integer mark: subjectMarks) {
	    marksString.append(mark).append(" ");
	}
	return marksString.toString();
    }
    
    // Used to get all student record
    public List<Student> getAllStudents() {
	List<Student> students = new ArrayList<Student>();
	
	try {
	    String query = "SELECT * FROM students";
	    Statement statement = connection.createStatement();
	    ResultSet rs = statement.executeQuery(query);
	    
	    while(rs.next()) {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int rollno = rs.getInt("roll_no");
		List<Integer> subjectMarks = marksStringToList(rs.getString("marks"));
		
		Student student = new Student(name, rollno, subjectMarks);
		student.id = id;
		students.add(student);
	    }
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return students;
    }
    
    // Used to convert marks string to list as it is stored as list of integers in java
    private List<Integer> marksStringToList(String subjectMarks) {
	List<Integer> marks = new ArrayList<Integer>();
	String[] marksStr = subjectMarks.split(" ");
	for(String mark: marksStr) {
	    marks.add(Integer.parseInt(mark));
	}
	return marks;
    }
    
    // Used to update student record in the database
    public boolean updateStudent(Student student) {
	try {
	    String query = "UPDATE students SET name = ?, roll_no = ?, marks = ? WHERE id = ?";
	    PreparedStatement ps = connection.prepareStatement(query);
	    ps.setString(1, student.getName());
	    ps.setInt(2, student.getRollNo());
	    ps.setString(3, marksListToString(student.getSubjectMarks()));
	    ps.setInt(4, student.getId());
	    
	    return ps.executeUpdate() > 0;
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }
    
    // Used to delete student record from the database
    public boolean deleteStudent(int rollNo) {
	try {
	    String query = "DELETE FROM students WHERE roll_no = ?";
	    PreparedStatement ps = connection.prepareStatement(query);
	    ps.setInt(1, rollNo);
	    
	    return ps.executeUpdate() > 0;
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

}
