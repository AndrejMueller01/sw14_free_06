package com.studyprogress.objects;

import com.studyprogress.R;

public class University {
	
	private String name;
	private int id;
	
	public University(){
		
	}
	public University(String name, int id){
		this.name = name;
		this.id = id;
		
	}
	public String getName(){
		if(name == null)
			return String.valueOf(R.string.not_avaiable);
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	@Override
	public boolean equals(Object other){
		if(((University) other).getName().equals(this.name) && ((University) other).getId() == this.id){
			return true;
		}
		return false;
	}
}
