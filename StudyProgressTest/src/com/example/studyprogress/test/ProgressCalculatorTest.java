package com.example.studyprogress.test;

import java.io.InputStream;

import android.test.InstrumentationTestCase;

import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.ProgressCalculator;
import com.studyprogress.tools.XMLParser;

public class ProgressCalculatorTest extends InstrumentationTestCase{
	private InputStream is;
	private XMLParser parser;
	private ProgressCalculator pc;

	protected void setUp() throws Exception {
		super.setUp();
		is = getInstrumentation().getContext().getAssets().open("test_courses.xml");
		parser = XMLParser.getInstance(is);
		parser.parseCourses(false);	
		pc = new ProgressCalculator(parser);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testCalcuateCurrentECTS(){
		parser.getCurrentCourses().get(0).setStatus(GlobalProperties.STATUS_DONE);

		float currentECTS  = pc.calcuateCurrentECTS();
		assertEquals(currentECTS,parser.getCurrentCourses().get(0).getEcts());
	}
	public void getMaxECTS(){
		parser.getCurrentCurriculum().setMode(GlobalProperties.BACH_STUD);
		assertEquals(pc.getMaxECTS(true), GlobalProperties.BACH_ECTS);
		parser.getCurrentCurriculum().setMode(GlobalProperties.DIPL_STUD);
		assertEquals(pc.getMaxECTS(true), GlobalProperties.DIPLSTUD_ECTS);
		parser.getCurrentCurriculum().setMode(GlobalProperties.MAST_STUD);
		assertEquals(pc.getMaxECTS(true), GlobalProperties.MASTER_ECTS);
		parser.getCurrentCurriculum().setMode(GlobalProperties.LA_STUD);
		assertEquals(pc.getMaxECTS(true), GlobalProperties.LAMTSTUD_ECTS);
		parser.getCurrentCurriculum().setMode(GlobalProperties.PHD_STUD);
		assertEquals(pc.getMaxECTS(true), GlobalProperties.PHD_STUD_ECTS);
	}
	 

}
