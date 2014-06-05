package com.example.studyprogress.test;

import java.util.ArrayList;

import com.studyprogress.properties.GlobalProperties;

import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculumActivity;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CreateOptionalCoursesActivity;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.UniversityListViewActivity;
import com.studyprogress.objects.Course;
import com.studyprogress.tools.XMLParser;

public class SafeFeatureTests extends
ActivityInstrumentationTestCase2<ChooseStartConfigurationActivity> {
	
	private Solo solo;
	MenuItem safeitem;
	MenuItem addNewCourse;
	Button newCurriculum;
	Button openCurriculum;
	ListView sem1lv;
	ListView sem2lv;
	ListView sem3lv;
	ListView sem4lv;
	ListView sem5lv;
	ListView sem6lv;
	ListView semopt;

	XMLParser parser;

	
	public SafeFeatureTests() {
		super(ChooseStartConfigurationActivity.class);
	}

	
	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		newCurriculum = (Button) solo
				.getView(R.id.choose_start_configuration_button_new);
		openCurriculum = (Button) solo
				.getView(R.id.choose_start_configuration_button_open);
		parser = XMLParser.getInstance(null);

	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	
	public void testSafeCurriculum() throws Exception {
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
		solo.clickOnView(curriculumListView.getChildAt(2));

		solo.waitForActivity(MainActivity.class);
		solo.pressMenuItem(0);
		
		
		solo = new Solo(getInstrumentation(), getActivity());
		solo.goBack();
		solo.clickOnButton(solo.getString(R.string.open_plan));

		String telematik = new String("Telematik");
		assert(parser.getCurrentCurriculum().getName().equals(telematik));

		
		
	}
	
	public void testProgressSafed() throws Exception
	{
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

		
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		solo.clickOnView(sem1lv.getChildAt(0));
		solo.clickOnButton(solo.getString(R.string.done));
		solo.waitForDialogToClose();
		//solo.wait(2000);
		//solo.pressMenuItem(0);
		solo.goBack();
		solo.clickOnButton("Ja");
		
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_plan));
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		String text = sem1lv.getItemAtPosition(0).toString();
		ArrayList<Course> currCourses = XMLParser.getInstance(null).getCurrentCourses();
		for(int i = 0; i<currCourses.size();i++)
		{
			if(text.equals(currCourses.get(i).getCourseName()))
			{
				assertEquals(GlobalProperties.STATUS_DONE, currCourses.get(i).getStatus());
			}
		}
		
	}

	public void testProgressSafedMoreCourses() throws Exception
	{
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

		
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		solo.clickOnView(sem1lv.getChildAt(0));
		solo.clickOnButton(solo.getString(R.string.done));
		solo.waitForDialogToClose();
		
		solo.clickOnView(sem1lv.getChildAt(1));
		solo.clickOnButton(solo.getString(R.string.inprogress));
		solo.waitForDialogToClose();

		solo.clickOnView(sem1lv.getChildAt(2));
		solo.clickOnButton(solo.getString(R.string.inprogress));
		solo.waitForDialogToClose();

		solo.clickOnView(sem1lv.getChildAt(3));
		solo.clickOnButton(solo.getString(R.string.done));
		solo.waitForDialogToClose();

		
		solo.goBack();
		solo.clickOnButton("Ja");
		
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_plan));
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		
		String text = sem1lv.getItemAtPosition(0).toString();
		ArrayList<Course> currCourses = XMLParser.getInstance(null).getCurrentCourses();
		for(int i = 0; i<currCourses.size();i++)
		{
			if(text.equals(currCourses.get(i).getCourseName()))
			{
				assertEquals(GlobalProperties.STATUS_DONE, currCourses.get(i).getStatus());
			}
		}

		String text2 = sem1lv.getItemAtPosition(1).toString();
		for(int i = 0; i<currCourses.size();i++)
		{
			if(text2.equals(currCourses.get(i).getCourseName()))
			{
				assertEquals(GlobalProperties.STATUS_IN_PROGRESS, currCourses.get(i).getStatus());
			}
		}
		
		String text3 = sem1lv.getItemAtPosition(2).toString();
		for(int i = 0; i<currCourses.size();i++)
		{
			if(text3.equals(currCourses.get(i).getCourseName()))
			{
				assertEquals(GlobalProperties.STATUS_IN_PROGRESS, currCourses.get(i).getStatus());
			}
		}
		
		String text4 = sem1lv.getItemAtPosition(3).toString();
		for(int i = 0; i<currCourses.size();i++)
		{
			if(text4.equals(currCourses.get(i).getCourseName()))
			{
				assertEquals(GlobalProperties.STATUS_DONE, currCourses.get(i).getStatus());
			}
		}
	}	

	
	//this TC fails!!
	public void testAllCoursesDone() throws Exception
	{
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
		
		solo.pressMenuItem(1);
		solo.waitForActivity(CreateOptionalCoursesActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		EditText courseNameET = (EditText) solo
				.getView(R.id.create_course_course_name_edit_text);
		EditText ectsET = (EditText) solo.getView(R.id.create_course_ects_edit_text);
		solo.enterText(courseNameET, "Freifach");
		solo.enterText(ectsET, "12");
        Spinner semSP = (Spinner) solo.getView(R.id.create_course_sem_spinner);
		solo.pressSpinnerItem(1, 6);
		solo.pressMenuItem(0);
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());	
		
		sem1lv = (ListView) solo.getView(R.id.courses_list_view_sem1);
		solo.clickOnButton(solo.getString(R.string.sem_1));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(1).length;i++)
		{
			solo.clickOnView(sem1lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}
		
		
		sem2lv = (ListView) solo.getView(R.id.courses_list_view_sem2);
		solo.clickOnButton(solo.getString(R.string.sem_2));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(2).length;i++)
		{
			solo.clickOnView(sem2lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}

		sem3lv = (ListView) solo.getView(R.id.courses_list_view_sem3);
		solo.clickOnButton(solo.getString(R.string.sem_3));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(3).length;i++)
		{
			solo.clickOnView(sem3lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}

		sem4lv = (ListView) solo.getView(R.id.courses_list_view_sem4);
		solo.clickOnButton(solo.getString(R.string.sem_4));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(4).length;i++)
		{
			solo.clickOnView(sem4lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}
	
		sem5lv = (ListView) solo.getView(R.id.courses_list_view_sem5);
		solo.clickOnButton(solo.getString(R.string.sem_5));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(5).length;i++)
		{
			solo.clickOnView(sem5lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}
		
		sem6lv = (ListView) solo.getView(R.id.courses_list_view_sem5);
		solo.clickOnButton(solo.getString(R.string.sem_6));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(6).length;i++)
		{
			solo.clickOnView(sem6lv.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}
		
		semopt = (ListView) solo.getView(R.id.courses_list_view_opt_courses);
		solo.clickOnButton(solo.getString(R.string.sem_add));
		for(int i = 0; i< XMLParser.getInstance(null).getCourseNamesOfSemester(7).length;i++)
		{
			solo.clickOnView(semopt.getChildAt(i));
			solo.clickOnButton(solo.getString(R.string.done));
			solo.waitForDialogToClose();
		}
	
		solo.goBack();
		solo.clickOnButton("Ja");
		
		solo.waitForActivity(ChooseStartConfigurationActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(R.string.open_plan));
		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		
		assertEquals(180, XMLParser.getInstance(null).getCurrentEcts());
		
	}	
}
