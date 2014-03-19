package com.example.studyprogress;

import java.util.ArrayList;

public class Curriculum {
	
	private String name;
	private ArrayList<Course> mandatoryCourseList;
	private ArrayList<Course> optionalCourseList;
	private int yearOfRelease;
	private int nameOfMajor;
	public Curriculum()
	{
		mandatoryCourseList = new ArrayList<Course>();
		optionalCourseList = new ArrayList<Course>();
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	
	

}
