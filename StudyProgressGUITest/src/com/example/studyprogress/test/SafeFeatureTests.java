package com.example.studyprogress.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculumActivity;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.UniversityListViewActivity;
import com.studyprogress.tools.XMLParser;

public class SafeFeatureTests extends
ActivityInstrumentationTestCase2<ChooseStartConfigurationActivity> {
	
	private Solo solo;
	ListView sem2lv;
	MenuItem safeitem;
	MenuItem addNewCourse;
	Button newCurriculum;
	Button openCurriculum;

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
	
	

}
