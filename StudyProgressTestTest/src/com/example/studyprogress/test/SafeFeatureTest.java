package com.example.studyprogress.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.studyprogress.R;
import com.robotium.solo.Solo;
import com.studyprogress.ChooseExistingOrNewCurriculum;
import com.studyprogress.ChooseStartConfigurationActivity;
import com.studyprogress.CurriculumListViewActivity;
import com.studyprogress.MainActivity;
import com.studyprogress.tools.XMLParser;

public class SafeFeatureTest extends
ActivityInstrumentationTestCase2<ChooseStartConfigurationActivity> {
	
	private Solo solo;
	ListView sem2lv;
	MenuItem safeitem;
	MenuItem addNewCourse;
	Button newCurriculum;
	Button openCurriculum;

	XMLParser parser;

	
	public SafeFeatureTest() {
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

	
	public void testSafeCurriculum() throws Exception {
		solo.waitForActivity(ChooseStartConfigurationActivity.class);

		solo.clickOnButton("NEU");
		solo.waitForActivity(ChooseExistingOrNewCurriculum.class);
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("Template");
		
		solo.waitForActivity(CurriculumListViewActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());

		
		ListView curriculumListView = (ListView) solo
				.getView(R.id.curriculum_list_view);
		solo.clickOnView(curriculumListView.getChildAt(2));

		solo.waitForActivity(MainActivity.class);
		solo = new Solo(getInstrumentation(), getActivity());
		//solo.clickOnButton("2");
		//solo.clickOnView(sem2lv.getChildAt(0));
		//solo.clickOnButton("Geschafft");
		solo.goBack();
		solo.goBack();
		solo.goBack();
		solo.clickOnButton("ÖFFNEN");
		
		//TODO: hier jetzt checken ob sich Telematik Curriculum öffnet!


		
		
	}
	
	

}
