package com.example.studyprogress.test;

import java.util.ArrayList;

import com.robotium.solo.Solo;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.studyprogress.R;

public class CurriculumListViewActivityTest extends
		ActivityInstrumentationTestCase2<CurriculumListViewActivity> {

	private Solo solo;
	private ListView lview;
	private Adapter adapter;

	// private ArrayAdapter<String> exampleAdapter;
	// private ArrayList<String> curriculumSampleNames;

	public CurriculumListViewActivityTest() {
		super(CurriculumListViewActivity.class);

	}

	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		lview = (ListView) solo.getView(R.id.curriculum_list_view);
		adapter = lview.getAdapter();
		/*
		 * curriculumSampleNames = new ArrayList<String>();
		 * curriculumSampleNames.add("Computer Science");
		 * curriculumSampleNames.add("Telematics");
		 * curriculumSampleNames.add("Chemistry");
		 * 
		 * exampleAdapter = new ArrayAdapter<String>(getActivity(),
		 * R.layout.curriculum_list_item, R.id.curriculum_text_view,
		 * curriculumSampleNames);
		 */
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testClickAllCurriculumListViewItems() throws Exception {

		solo.waitForActivity(CurriculumListViewActivity.class);

		ListView lview = (ListView) solo.getView(R.id.curriculum_list_view);
		int listSize = lview.getChildCount();
		for (int i = 0; i < listSize; i++) {
			solo.clickOnView(lview.getChildAt(i));
			solo.waitForActivity(MainActivity.class);
			solo.goBack();
		}

	}

	public void testSearchField() throws Exception {
		solo.waitForActivity(CurriculumListViewActivity.class);
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
