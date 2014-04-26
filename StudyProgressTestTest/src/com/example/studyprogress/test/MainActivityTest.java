package com.example.studyprogress.test;

import com.robotium.solo.Solo;
import com.studyprogress.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.studyprogress.R;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	ListView sem1lv;
	ListView sem2lv;
	ListView sem3lv;
	ListView sem4lv;
	ListView sem5lv;
	ListView sem6lv;
	MenuItem safeitem;

	public MainActivityTest() {
		super(MainActivity.class);

	}

	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		sem2lv = (ListView) solo.getView(R.id.courses_list_view_sem2);
		sem3lv = (ListView) solo.getView(R.id.courses_list_view_sem3);
		sem4lv = (ListView) solo.getView(R.id.courses_list_view_sem4);
		sem5lv = (ListView) solo.getView(R.id.courses_list_view_sem5);
		sem6lv = (ListView) solo.getView(R.id.courses_list_view_sem6);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	@Override
	public MainActivity getActivity() {
		// TODO: test file
		Intent intent = new Intent();
		intent.putExtra("firstOpen", false);
		setActivityIntent(intent);
		return super.getActivity();
	}

	public void testVisibilityOfListViews() throws Exception {

		solo.waitForActivity(MainActivity.class);

		solo.clickOnButton("1");
		solo.clickOnView(sem1lv.getChildAt(0));
		solo.goBack();
		solo.clickOnButton("2");
		solo.clickOnView(sem2lv.getChildAt(0));
		solo.goBack();
		solo.clickOnButton("3");
		solo.clickOnView(sem3lv.getChildAt(0));
		solo.goBack();

		solo.clickOnButton("4");
		solo.clickOnView(sem4lv.getChildAt(0));
		solo.goBack();

		solo.clickOnButton("5");
		solo.clickOnView(sem5lv.getChildAt(0));
		solo.goBack();

		solo.clickOnButton("6");
		solo.clickOnView(sem6lv.getChildAt(0));
		solo.goBack();

	}
	
	public void testAlertDialog() throws Exception
	{
		solo.waitForActivity(MainActivity.class);
		solo.clickOnButton("1");
		solo.clickOnView(sem1lv.getChildAt(0));
		solo.clickOnButton("Done");
		solo.pressMenuItem(0);
		solo.goBack();
		
		

		
	}

}
