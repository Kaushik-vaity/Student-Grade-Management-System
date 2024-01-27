package com.codtechitsolutions.studentgrademanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This class is the starting point of the application
public class StudentGradeManagementSystem {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	
	while(true) {
	    // Menu for what operation you need to perform
	    System.out.println("----------Student Grade Management System----------");
	    System.out.println("1. Add Student");
	    System.out.println("2. View Students");
	    System.out.println("3. Update Student");
	    System.out.println("4. Delete Student");
	    System.out.println("5. Exit");
	    
	    System.out.println("Enter your choice:");
	    int choice = sc.nextInt();
	    
	    //  Based on user's choice respective case will be performed
	    switch(choice) {
        	    case 1: addStudent(); break;
        	    case 2: viewStudents(); break;
        	    case 3: updateStudent(); break;
        	    case 4: deleteStudent(); break;
        	    case 5: System.out.println("Exiting. Bye!");
        	    	     sc.close();
        	    	     System.exit(0);
    	    	    default: System.out.println("Invalid choice. Please enter valid choice."); break;
	    }
	}
    }
    
    // This method is used to get user input for student to be deleted by roll no 
    private static void deleteStudent() {
	StudentDatabase sd = new StudentDatabase();
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Enter the Roll No of the student to delete:");
	int rollNo = sc.nextInt();
	
	if(sd.deleteStudent(rollNo)) {
	    System.out.println("Student deleted successfully!");
	} else {
	    System.out.println("Error deleting student.");
	}
	
    }
    
 // This method is used to get user input for student to be updated by roll no 
    private static void updateStudent() {
	StudentDatabase sd = new StudentDatabase();
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Enter Roll No of a student to update:");
	int rollNo = sc.nextInt();
	
	Student studentToUpdate = findStudentByRollNo(rollNo);
	
	if(studentToUpdate != null) {
	    System.out.println("Enter new marks for the subjects:");
	    List<Integer> newMarks = new ArrayList<Integer>();
	    for(int i = 0; i < studentToUpdate.getSubjectMarks().size(); i++) {
		System.out.println("Subject "+(i+1)+": ");
		newMarks.add(sc.nextInt());
	    }
	    studentToUpdate.getSubjectMarks().clear();
	    studentToUpdate.getSubjectMarks().addAll(newMarks);
	    
	    if(sd.updateStudent(studentToUpdate)) {
		System.out.println("Student updated successfully!");
	    } else {
		System.out.println("Error updating student.");
	    }
	} else {
	    System.out.println("Student not found.");
	}
    }
    
    // This method is used to find students by roll no
    private static Student findStudentByRollNo(int rollNo) {
	StudentDatabase sd = new StudentDatabase();
		
	List<Student> students = sd.getAllStudents();
	for(Student student: students) {
	    if(student.getRollNo() == rollNo) {
		return student;
	    }
	}
	return null;
    }
    
    // This method is used to view all students in console
    private static void viewStudents() {
	StudentDatabase sd = new StudentDatabase();
	List<Student> students = sd.getAllStudents();
	
	if(students.isEmpty()) {
	    System.out.println("No students found.");
	    return;
	}
	
	for(Student student: students) {
	    System.out.println("-----Student Details-----");
	    System.out.println("Id: "+student.getId());
	    System.out.println("Name: "+student.getName());
	    System.out.println("Roll No.: "+student.getRollNo());
	    System.out.println("Subject Marks: "+student.getSubjectMarks());
	    System.out.println("Percentage: "+student.calculatePercentage());
	    System.out.println("Grade: "+student.calculateGrade());
	}
    }
    
    // This method is used to add new student to the memory/database
    private static void addStudent() {
	StudentDatabase sd = new StudentDatabase();
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Enter student name:");
	String name = sc.next();
	System.out.println("Enter Roll No.:");
	int rollno = sc.nextInt();
	System.out.println("Enter number of subjects:");
	int noOfSubjects = sc.nextInt();
	
	List<Integer> subjectsMarks = new ArrayList<>();
	for(int i = 0; i < noOfSubjects; i++) {
	    System.out.println("Enter the marks for the subject:"+(i+1)+":");
	    subjectsMarks.add(sc.nextInt());
	}
	
	Student student = new Student(name, rollno, subjectsMarks);
	int studentId = sd.addStudent(student);
	
	if(studentId != -1) {
	    System.out.println("Student added succcessfully to the database with Id:"+studentId);
	} else {
	    System.out.println("Error while adding new student.");
	}
    }
}
