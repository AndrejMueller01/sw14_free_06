package com.studyprogress.properties;

import android.annotation.SuppressLint;
import android.app.Activity;

public class ActionBarProperties {

	@SuppressLint("NewApi")
	public static void standardMainActivityMenu(Activity a){
		a.getActionBar().setTitle("");
		a.getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@SuppressLint("NewApi")
	public static void noTitleText(Activity a){
		a.getActionBar().setTitle("");
	}
}
