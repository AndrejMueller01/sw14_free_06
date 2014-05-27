package com.example.studyprogress.test;

import com.example.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculum;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CreateOptionalCourses;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.tools.XMLParser;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AddNewCoursesTests extends
		ActivityInstrumentationTestCase2<ChooseStartConfigurationActivity> {
	private Solo solo;
	ListView sem1lv;
	ListView sem2lv;
	ListView sem3lv;
	ListView sem4lv;
	ListView sem5lv;
	ListView sem6lv;
	MenuItem safeitem;
	MenuItem addNewCourse;
	Button newCurriculum;
	Button openCurriculum;

	XMLParser parser;
	EditText courseNameET;
	EditText ectsET;
	Spinner semSP;
	Spinner modeSP;

	public AddNewCoursesTests() {
		super(ChooseStartConfigurationActivity.class);
	}

	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		newCurriculum = (Button) solo
				.getView(R.id.choose_start_configuration_button_new);
		openCurriculum = (Button) solo
				.getView(R.id.choose_start_configuration_button_open);

	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testAddNewCourse() throws Exception {

		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton("NEU");
		solo.waitForActivity(ChooseExistingOrNewCurriculum.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("Template");
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		
		ListView curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(0));

		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.pressMenuItem(1);

		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());

		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);

		solo.enterText(courseNameET, "TestKurs");
		solo.enterText(ectsET, "3");
		solo.pressSpinnerItem(0, 1);

		solo.pressSpinnerItem(1, 1);

		solo.pressMenuItem(0);

		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		sem2lv = (ListView) solo.getView(R.id.courses_list_view_sem2);

		solo.clickOnButton("2");
		assertEquals(sem2lv.getAdapter().getItem(sem2lv.getCount() - 1),
				"TestKurs");
	}
}
