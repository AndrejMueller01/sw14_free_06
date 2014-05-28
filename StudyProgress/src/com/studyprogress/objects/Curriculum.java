package com.studyprogress.objects;

import java.util.ArrayList;

public class Curriculum {
	
	private String name;
	private int curriculumId;
	private ArrayList<Course> mandatoryCourseList;
	private ArrayList<Course> optionalCourseList;
	private int yearOfRelease;
	private int isDiplSt;
	private int isMasterSt;

	public Curriculum()
	{
		mandatoryCourseList = new ArrayList<Course>();
		optionalCourseList = new ArrayList<Course>();
		isDiplSt = 0;

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
	
	public void setDiplSt(int diplSt) {
		isDiplSt = diplSt;
	}
	public int getDiplSt() {
		return isDiplSt;
	}		

}
