package com.example.studyprogress.test;

import java.io.InputStream;

import android.test.InstrumentationTestCase;

import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.tools.XMLParser;

public class CourseListAdapterTest extends InstrumentationTestCase{
	private InputStream is;
	private CourseListAdapter cla;
	private XMLParser parser;

	protected void setUp() throws Exception {
		super.setUp();
		is = getInstrumentation().getContext().getAssets().open("test_courses.xml");
		parser = XMLParser.getInstance(is);
		parser.parseCourses(false);
		cla = new CourseListAdapter(parser.getCurrentCoursesNames(), getInstrumentation().getContext());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testGetCount(){
		assertEquals(2,cla.getCount());
	}
	public void testSetCourseNamesAfterDeletingAnElement(){
		String[] new_names = {"Analysis T1 VU"};
		cla.setCourseNamesAfterDeletingAnElement(new_names, 1);
		assertEquals(cla.getPositionByString("Analysis T1 VU"),0);
		
	}
	
	

}
