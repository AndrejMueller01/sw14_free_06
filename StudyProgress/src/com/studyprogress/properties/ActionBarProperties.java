package com.studyprogress.properties;

import android.app.Activity;

public class ActionBarProperties {
    public static String EMPTY_STRING = "";
	public static void standardMainActivityMenu(Activity a){
		a.getActionBar().setTitle(EMPTY_STRING);
		a.getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	public static void noTitleText(Activity a){
		a.getActionBar().setTitle(EMPTY_STRING);
	}
}
