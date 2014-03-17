package com.example.studyprogress;

import java.util.ArrayList;

public class Curriculum {
	
	private ArrayList<Course> mandatoryCourseList;
	private ArrayList<Course> optionalCourseList;
	private int yearOfRelease;
	private int nameOfMajor;
	public Curriculum(int yearOfRelease, int nameOfMajor)
	{
		this.yearOfRelease = yearOfRelease;
		this.nameOfMajor = nameOfMajor;
		mandatoryCourseList = new ArrayList<Course>();
		optionalCourseList = new ArrayList<Course>();
	}
	
	

}
