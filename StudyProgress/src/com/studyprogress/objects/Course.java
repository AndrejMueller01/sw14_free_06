package com.studyprogress.objects;

public class Course {
	
	private String courseName;
	private float ects;
	private String courseNumber;
	private boolean isBachelorCourse;
	private int semester;
	private int curricula;
	private int status;
	
	public Course(String courseName, float ects, String courseNumber, boolean isBachelorCourse, int semester, int curricula)
	{
		this.courseName = courseName;
		this.ects = ects;
		this.courseNumber = courseNumber;
		this.isBachelorCourse = isBachelorCourse;
		this.semester = semester;
		this.curricula = curricula;
	}


	public Course() {
		status = 0;
		// TODO Auto-generated constructor stub
	}


	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public float getEcts() {
		return ects;
	}
	public void setEcts(float ects) {
		this.ects = ects;
	}
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public boolean isBachelorCourse() {
		return isBachelorCourse;
	}
	public void setBachelorCourse(boolean isBachelorCourse) {
		this.isBachelorCourse = isBachelorCourse;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getCurricula() {
		return curricula;
	}
	public void setCurricula(int curricula) {
		this.curricula = curricula;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}	
}
