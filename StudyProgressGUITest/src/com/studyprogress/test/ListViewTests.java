package com.studyprogress.test;

import java.util.ArrayList;

import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculumActivity;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.UniversityListViewActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.studyprogress.R;

public class ListViewTests extends
		ActivityInstrumentationTestCase2<ChooseStartConfigurationActivity> {

	private Solo solo;
	private ListView lview;
	private Adapter adapter;

	// private ArrayAdapter<String> exampleAdapter;
	// private ArrayList<String> curriculumSampleNames;

	public ListViewTests() {
		super(ChooseStartConfigurationActivity.class);

	}

	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());


		
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

//	public void testClickAllCurriculumListViewItems() throws Exception {
//		solo.waitForActivity(ChooseStartConfigurationActivity.class);
//
//		solo.clickOnButton(solo.getString(R.string.new_plan));
//		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
//		solo = new Solo(getInstrumentation(), getActivity());
//		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
//		
//		solo.waitForActivity(UniversityListViewActivity.class);
//		solo = new Solo(getInstrumentation(), getActivity());
//		ListView universityListView = (ListView) solo
//				.getView(R.id.university_list_view);
//		solo.clickOnView(universityListView.getChildAt(0));	
//		
//		solo.waitForActivity(CurriculumListViewActivity.class);
//		solo = new Solo(getInstrumentation(), getActivity());
//		
//		lview = (ListView) solo.getView(R.id.curriculum_list_view);
//		adapter = lview.getAdapter();
//		
//		int listSize = lview.getChildCount();
//		for (int i = 0; i < listSize; i++) {
//			solo.clickOnView(lview.getChildAt(i));
//			solo.waitForActivity(MainActivity.class);
//			solo = new Solo(getInstrumentation(), getActivity());
//			solo.goBack();
//		}
//
//	}

	public void testSearchField() throws Exception {
		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
		
		solo.waitForActivity(UniversityListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		ListView universityListView = (ListView) solo
				.getView(R.id.university_list_view);
		solo.clickOnView(universityListView.getChildAt(0));	
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		lview = (ListView) solo.getView(R.id.curriculum_list_view);
		adapter = lview.getAdapter();
		
		
		EditText searchField = (EditText) solo
				.getView(R.id.crurriculum_list_view_search_input_field);
		solo.enterText(searchField, "W");
		checkIfStringStartsWith("W");
		solo.enterText(searchField, "e");
		checkIfStringStartsWith("We");

		solo.clearEditText(searchField);
		solo.enterText(searchField, "C");
		checkIfStringStartsWith("C");


	}

	public void checkIfStringStartsWith(String startString) {
		for (int i = 0; i < adapter.getCount(); i++) 
			assertTrue(adapter.getItem(i).toString().startsWith(startString));
		solo.sleep(1000);
	}
}
