package com.studyprogress.objects;

import java.util.ArrayList;

public class Curriculum {
	
	private String name;
	private int curriculumId;
	private ArrayList<Course> mandatoryCourseList;
	private ArrayList<Course> optionalCourseList;
	private int yearOfRelease;
<<<<<<< HEAD
	private int isDiplSt;
	private int isMasterSt;
=======
	private int mode;
>>>>>>> d977bacfb00eca38cc175dde7070d9c51c1ab4fe

	public Curriculum()
	{
		mandatoryCourseList = new ArrayList<Course>();
		optionalCourseList = new ArrayList<Course>();
		mode = 0;

	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setCurriculumId(int curriculumId){
		this.curriculumId = curriculumId;
	}
	public int getCurriculumId(){
		return curriculumId;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getMode() {
		return mode;
	}		

}
