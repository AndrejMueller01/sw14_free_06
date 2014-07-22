package com.studyprogress.test;

import java.util.ArrayList;

import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculumActivity;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CreateNewCurriculumActivity;
import com.studyprogress.CreateOptionalCoursesActivity;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.UniversityListViewActivity;
import com.studyprogress.objects.Course;
import com.studyprogress.tools.XMLParser;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class CurriculaTests extends
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

	public CurriculaTests() {
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

	public void testChoosePredefinedCurricula() throws Exception {

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

		
		ListView curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(0));

		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("Informatik", XMLParser.getInstance(null).getCurrentCurriculum().getName());
		
		solo.goBack();
		
		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
		
		solo.waitForActivity(UniversityListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		universityListView = (ListView) solo
				.getView(R.id.university_list_view);
		solo.clickOnView(universityListView.getChildAt(0));	
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		
		curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(1));
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("Informatik Master", XMLParser.getInstance(null).getCurrentCurriculum().getName());
		
		solo.goBack();

		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
		
		solo.waitForActivity(UniversityListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		universityListView = (ListView) solo
				.getView(R.id.university_list_view);
		solo.clickOnView(universityListView.getChildAt(0));	
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		
		curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(2));
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("Telematik", XMLParser.getInstance(null).getCurrentCurriculum().getName());
		
		solo.goBack();

		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_predefined_curriculum));
		
		solo.waitForActivity(UniversityListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		universityListView = (ListView) solo
				.getView(R.id.university_list_view);
		solo.clickOnView(universityListView.getChildAt(1));	
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		
		curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(0));
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("Diplomstudium der Rechtswissenschaften", XMLParser.getInstance(null).getCurrentCurriculum().getName());		
	}
	
	
	public void testCreateNewCurriculas() throws Exception {
		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.empty_plan));
		solo.waitForActivity(CreateNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		EditText currName = (EditText) solo.getView(R.id.create_new_curr_name_edit_text);
		Spinner currModeSp = (Spinner) solo.getView(R.id.create_new_curr_mode_spinner);
		
		solo.enterText(currName, "TestCurr");
		//solo.pressSpinnerItem(0, 1);
		
		solo.pressMenuItem(0);
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("TestCurr", XMLParser.getInstance(null).getCurrentCurriculum().getName());	
		assertEquals(0, XMLParser.getInstance(null).getCurrentCurriculum().getMode());
		solo.goBack();
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		//TODO: #Semester hier auch testen!!!
		//-----
		
		
		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.empty_plan));
		solo.waitForActivity(CreateNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		currName = (EditText) solo.getView(R.id.create_new_curr_name_edit_text);
		currModeSp = (Spinner) solo.getView(R.id.create_new_curr_mode_spinner);
		
		solo.enterText(currName, "TestCurr2");
		solo.pressSpinnerItem(0, 1);
		
		solo.pressMenuItem(0);
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("TestCurr2", XMLParser.getInstance(null).getCurrentCurriculum().getName());	
		assertEquals(1, XMLParser.getInstance(null).getCurrentCurriculum().getMode());
		solo.goBack();
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		//-----
		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.empty_plan));
		solo.waitForActivity(CreateNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		currName = (EditText) solo.getView(R.id.create_new_curr_name_edit_text);
		currModeSp = (Spinner) solo.getView(R.id.create_new_curr_mode_spinner);
		
		solo.enterText(currName, "TestCurr3");
		solo.pressSpinnerItem(0, 2);
		
		solo.pressMenuItem(0);
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("TestCurr3", XMLParser.getInstance(null).getCurrentCurriculum().getName());	
		assertEquals(2, XMLParser.getInstance(null).getCurrentCurriculum().getMode());
		solo.goBack();
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		//--
		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.empty_plan));
		solo.waitForActivity(CreateNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		currName = (EditText) solo.getView(R.id.create_new_curr_name_edit_text);
		currModeSp = (Spinner) solo.getView(R.id.create_new_curr_mode_spinner);
		
		solo.enterText(currName, "TestCurr4");
		solo.pressSpinnerItem(0, 3);
		
		solo.pressMenuItem(0);
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("TestCurr4", XMLParser.getInstance(null).getCurrentCurriculum().getName());	
		assertEquals(3, XMLParser.getInstance(null).getCurrentCurriculum().getMode());
		solo.goBack();
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		//--
		solo.clickOnButton(solo.getString(R.string.new_plan));
		solo.waitForActivity(ChooseExistingOrNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.empty_plan));
		solo.waitForActivity(CreateNewCurriculumActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		currName = (EditText) solo.getView(R.id.create_new_curr_name_edit_text);
		currModeSp = (Spinner) solo.getView(R.id.create_new_curr_mode_spinner);
		
		solo.enterText(currName, "TestCurr5");
		solo.pressSpinnerItem(0, 4);
		
		solo.pressMenuItem(0);
		
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals("TestCurr5", XMLParser.getInstance(null).getCurrentCurriculum().getName());	
		assertEquals(4, XMLParser.getInstance(null).getCurrentCurriculum().getMode());
		solo.goBack();
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		
	}
}
