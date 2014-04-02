package com.example.studyprogress.test;

import com.robotium.solo.Solo;
import com.example.studyprogress.CurriculumListViewActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Adapter;
import android.widget.ListView;
import com.example.studyprogress.R;

public class UITest extends
		ActivityInstrumentationTestCase2<CurriculumListViewActivity> {

	private Solo solo;

	public UITest() {
		super(CurriculumListViewActivity.class);

	}

	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testClickOnView() throws Exception {
		// TODO: better
		solo.sleep(2000);

		ListView lview = (ListView) solo.getView(R.id.curriculum_list_view);
		int listSize = lview.getChildCount();
		View listItem = 


	}

}
