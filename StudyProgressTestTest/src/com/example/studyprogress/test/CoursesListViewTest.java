package com.example.studyprogress.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.MainActivity;


public class CoursesListViewTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public CoursesListViewTest() {
		super(MainActivity.class);
	}
	
	private Solo solo;
	private ListView lview;
	private Adapter adapter;
	
	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		lview = (ListView) solo.getView(R.id.courses_list_view);
		adapter = lview.getAdapter();
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testClickAllCoursesListViewItems() throws Exception {

		solo.waitForActivity(MainActivity.class);

		ListView lview = (ListView) solo.getView(R.id.courses_list_view);
		int listSize = lview.getChildCount();
		for (int i = 0; i < listSize; i++) {
			solo.clickOnView(lview.getChildAt(i));
			solo.waitForActivity(MainActivity.class);
			solo.goBack();
		}

	}

	
}
