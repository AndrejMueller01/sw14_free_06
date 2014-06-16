package com.studyprogress.objects;

public class Course {

	private String courseName;
	private float ects;
	private String courseNumber;
	private boolean isBachelorCourse;
	private int semester;
	private int curriculaNumber;
	private int status;
	private int steop;
	private String mode;

	public Course(String courseName, float ects, int semester, String mode) {
		this.courseName = courseName;
		this.ects = ects;
		this.semester = semester;
		this.mode = mode;
	}

	public Course() {
		status = 0;
		steop = 0;
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

	public int getCurriculaNumber() {
		return curriculaNumber;
	}

	public void setCurriculaNumber(int curricula) {
		this.curriculaNumber = curricula;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSteop() {
		return steop;
	}

	public void setSteop(int steop) {
		this.steop = steop;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
