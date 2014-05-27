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
import android.util.Log;
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
	ListView semopt;
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

	public void testAddOneNewCourse() throws Exception {

		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculum.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
		
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
		solo.clickOnButton(solo.getString(R.string.sem_2));
		assertEquals(sem2lv.getAdapter().getItem(sem2lv.getCount() - 1),
				"TestKurs");
	}
	
	
	public void testAddCoursesAllSemesters() throws Exception
	{
		
		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.open_plan));
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.pressMenuItem(1);

		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());

		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);

		solo.enterText(courseNameET, "Sem1");
		solo.enterText(ectsET, "3");
		//Modus not needed in v1
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 0);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());	
		
		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Sem2");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 1);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Sem3");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 2);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());		

		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Sem4");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 3);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());	
		
		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Sem5");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 4);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());		
			
		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Sem6");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 5);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());				
		
		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCourses.class);
		solo = new Solo(getInstrumentation(), getActivity());
		courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "SemOpt");
		solo.enterText(ectsET, "3");
		//solo.pressSpinnerItem(0, 0);
		solo.pressSpinnerItem(1, 6);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());		
		
		
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		Log.d("t5", "Count: "+sem1lv.getAdapter().getCount());
		assertEquals(sem1lv.getAdapter().getItem(sem1lv.getAdapter().getCount() -1),
				"Sem1");	
	
		//sem2lv = (ListView) solo.getView(R.id.courses_list_view_sem2);
		Log.d("t5", "Count: "+sem2lv.getAdapter().getCount());
		solo.clickOnButton(solo.getString(R.string.sem_2));
		assertEquals(sem2lv.getAdapter().getItem(1),
				"Sem2");			

		sem3lv = (ListView) solo.getView(R.id.courses_list_view_sem3);
		Log.d("t5", "Count: "+sem3lv.getAdapter().getCount());
		solo.clickOnButton(solo.getString(R.string.sem_3));
		assertEquals(sem3lv.getAdapter().getItem(sem3lv.getAdapter().getCount() -1),
				"Sem3");
		
		sem4lv = (ListView) solo.getView(R.id.courses_list_view_sem4);
		Log.d("t5", "Count: "+sem4lv.getAdapter().getCount());
		solo.clickOnButton(solo.getString(R.string.sem_4));
		assertEquals(sem4lv.getAdapter().getItem(sem4lv.getAdapter().getCount() -1),
				"Sem4");	

		sem5lv = (ListView) solo.getView(R.id.courses_list_view_sem5);
		Log.d("t5", "Count: "+sem5lv.getAdapter().getCount());

		solo.clickOnButton(solo.getString(R.string.sem_5));
		assertEquals(sem5lv.getAdapter().getItem(sem5lv.getAdapter().getCount() -1),
				"Sem5");		
				
		sem6lv = (ListView) solo.getView(R.id.courses_list_view_sem6);
		Log.d("t5", "Count: "+sem6lv.getAdapter().getCount());

		solo.clickOnButton(solo.getString(R.string.sem_6));
		assertEquals(sem6lv.getAdapter().getItem(sem6lv.getAdapter().getCount() -1),
				"Sem6");
		
		semopt = (ListView) solo.getView(R.id.courses_list_view_opt_courses);
		Log.d("t5", "Count: "+semopt.getAdapter().getCount());

		solo.clickOnButton(solo.getString(R.string.sem_add));
		assertEquals(semopt.getAdapter().getItem(semopt.getAdapter().getCount() -1),
				"SemOpt");		
	}
	
	
	
}
