package com.example.studyprogress.test;

import com.example.studyprogress.CurriculumView;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class CurriculumViewTest extends ActivityInstrumentationTestCase2<CurriculumView> {
	
	private Solo solo;

	public CurriculumViewTest()
	{
		super(CurriculumView.class);
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
