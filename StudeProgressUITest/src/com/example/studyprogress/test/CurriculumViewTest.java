package com.example.studyprogress.test;

import com.example.studyprogress.CurriculumListViewActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class CurriculumViewTest extends ActivityInstrumentationTestCase2<CurriculumListViewActivity> {
	
	private Solo solo;

	public CurriculumViewTest()
	{
		super(CurriculumListViewActivity.class);
	}
	
	@Override 
	protected void setUp()
	{
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testOnListClick()
	{
		//solo.clickOnView(getActivity().findViewById(R.id.curriculumListView));
	}

}
