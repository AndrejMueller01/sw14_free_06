package com.example.objects;

public class Course {
	private String courseName;
	private int ects;
	private String courseNumber;
	private boolean isBachelorCourse;
	private int semester;
	public Course(String courseName, int ects, String courseNumber, boolean isBachelorCourse, int semester)
	{
		this.courseName = courseName;
		this.ects = ects;
		this.courseNumber = courseNumber;
		this.isBachelorCourse = isBachelorCourse;
		this.semester = semester;
	}

	
}
