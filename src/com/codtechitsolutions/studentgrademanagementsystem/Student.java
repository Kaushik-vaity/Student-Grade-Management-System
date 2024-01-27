package com.codtechitsolutions.studentgrademanagementsystem;

import java.util.List;

// This class is used to create basic structure for data of the application
public class Student {
	
	int id;
	private String name;
	private int rollNo;
	private List<Integer> subjectMarks;
	
	public Student(String name, int rollNo, List<Integer> subjectMarks) {
		super();
		this.name = name;
		this.rollNo = rollNo;
		this.subjectMarks = subjectMarks;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getRollNo() {
		return rollNo;
	}

	public List<Integer> getSubjectMarks() {
		return subjectMarks;
	}
	
	// Method to calculate percentage from total marks scored 
	public double calculatePercentage() {
		int totalMarks = 0;
		for(int subjectMark : subjectMarks) {
			totalMarks += subjectMark;
		}
		return totalMarks / subjectMarks.size();
	}
	
	// Method to calculate grades based to percentage scored 
	public String calculateGrade() {
		double percentage = calculatePercentage();
		
		if(percentage > 90) {
		    return "A+";
		} else if(percentage > 75) {
		    return "A";
		} else if(percentage > 60) {
		    return "B";
		} else if(percentage > 50) {
		    return "C";
		} else if(percentage >= 35) {
		    return "D"; 
		} else {
		    return "Fail";
		}
	
	}
}
