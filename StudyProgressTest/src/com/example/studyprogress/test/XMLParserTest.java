package com.example.studyprogress.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;
import com.studyprogress.objects.University;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLParser;

public class XMLParserTest extends InstrumentationTestCase {
	XMLParser parser;
	InputStream is;

	protected void setUp() throws Exception {
		super.setUp();

		parser = new XMLParser(null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private void initParserAndSetInputStream(String fileName) {
		parser = new XMLParser(null);
		try {
			is = getInstrumentation().getContext().getAssets().open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
	}

	public void testGetCurriculaNames() {
		initParserAndSetInputStream("test_curricula.xml");
		parser.parseCurricula();

		ArrayList<String> testCurricula = new ArrayList<String>();

		testCurricula.add("TestCurriculum1");
		testCurricula.add("TestCurriculum2");
		testCurricula.add("TestCurriculum3");
		testCurricula.add("TestCurriculum4");
		testCurricula.add("TestCurriculum5");

		ArrayList<String> actualCurricula;

		try {
			actualCurricula = parser.getCurriculaNames(is);

			assertEquals(testCurricula.get(0), actualCurricula.get(0));
			assertEquals(testCurricula.get(1), actualCurricula.get(1));
			assertEquals(testCurricula.get(2), actualCurricula.get(2));
			assertEquals(testCurricula.get(3), actualCurricula.get(3));
			assertEquals(testCurricula.get(4), actualCurricula.get(4));

		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void testParseUniversities() {

		ArrayList<University> universities = new ArrayList<University>();
		University uni = new University("TU Graz", 1);
		universities.add(uni);
		uni = new University("KFU Graz", 2);
		universities.add(uni);

		initParserAndSetInputStream("test_universities.xml");
		parser.parseUniversities();

		assertEquals(universities, parser.getAllUniversities());

	}

	public void testParseCourses() {

		ArrayList<Course> courses = new ArrayList<Course>();
		//
		Course course = new Course("Analysis T1 VU", 7, 1, "VU");
		courses.add(course);
		course = new Course("Einführung in das Studium der Informatik VO", 1,
				5, "VO");
		courses.add(course);

		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		// TODO: override equals Method
		for (int i = 0; i < courses.size(); i++) {
			assertEquals(courses.get(i).getCourseName(), parser
					.getCurrentCourses().get(i).getCourseName());
			assertEquals(courses.get(i).getEcts(), parser.getCurrentCourses()
					.get(i).getEcts());
			assertEquals(courses.get(i).getSemester(), parser
					.getCurrentCourses().get(i).getSemester());
			assertEquals(courses.get(i).getMode(), parser.getCurrentCourses()
					.get(i).getMode());
		}

	}

	public void testParseCoursesSaveFile() {
		initParserAndSetInputStream("test_courses_saved.xml");
		parser.parseCourses(true);
		assertEquals("Informatik", parser.getCurrentCurriculum().getName());

	}

	public void testParseCurricula() {
		initParserAndSetInputStream("test_curricula.xml");
		parser.parseCurricula();
		for (int i = 0; i < parser.getCurricula().size(); i++)
			assertEquals("TestCurriculum" + (i + 1),
					parser.getCurricula().get(i).getName());

	}

	public void testGetCurriculumMode() {
		initParserAndSetInputStream("test_curricula.xml");
		parser.parseCurricula();
		assertEquals(parser.getCurriculumMode("TestCurriculum1"), 2);

	}

	public void testGetUniversityNames() {
		initParserAndSetInputStream("test_universities.xml");

		parser.parseUniversities();

		ArrayList<String> testUniversities = new ArrayList<String>();

		testUniversities.add("TU Graz");
		testUniversities.add("KFU Graz");

		ArrayList<String> actualUniversities;

		try {
			actualUniversities = parser.getUniversityNames(is);

			assertEquals(testUniversities.get(0), actualUniversities.get(0));
			assertEquals(testUniversities.get(1), actualUniversities.get(1));

		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void testGetCurriculumIdWithName() {
		initParserAndSetInputStream("test_curricula.xml");
		parser.parseCurricula();

		assertEquals(1, parser.getCurriculumIdWithName("TestCurriculum1"));
	}

	public void testGetUniversityIdWithName() {
		initParserAndSetInputStream("test_universities.xml");
		parser.parseUniversities();

		assertEquals(1, parser.getUniversityIdWithName("TU Graz"));
	}

	public void testSetStatusOfCurrentCourseTo() {
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		parser.setStatusOfCurrentCourseTo(0, GlobalProperties.STATUS_DONE);
		assertEquals(parser.getCurrentCourses().get(0).getStatus(),
				GlobalProperties.STATUS_DONE);

	}

	public void testGetCurrentCoursesNames() {
		String[] courseNames = new String[2];
		courseNames[0] = "Analysis T1 VU";
		courseNames[1] = "Einführung in das Studium der Informatik VO";
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);

		try {
			assertEquals(parser.getCurrentCoursesNames()[0], courseNames[0]);
			assertEquals(parser.getCurrentCoursesNames()[1], courseNames[1]);

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testGetCurrentNamesOfSemester() {
		String[] courseNames = new String[2];
		courseNames[0] = "Analysis T1 VU";
		courseNames[1] = "Einführung in das Studium der Informatik VO";
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);

		assertEquals(parser.getCourseNamesOfSemester(1)[0], courseNames[0]);
		assertEquals(parser.getCourseNamesOfSemester(5)[0], courseNames[1]);

	}

	public void testDeleteCourse() {
		initParserAndSetInputStream("test_courses.xml");
		String[] courseNames = new String[1];
		courseNames[0] = "Einführung in das Studium der Informatik VO";
		parser.parseCourses(false);
		parser.deleteCourse("Analysis T1 VU");
		try {
			assertEquals(parser.getCurrentCoursesNames()[0], courseNames[0]);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getEctsByName() {
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);

		assertEquals(7, parser.getEctsByName("Analysis T1 VU"));
	}

	public void testGetCourseNumberByName() {
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		assertEquals("501.446", parser.getCourseNumberByName("Analysis T1 VU"));
	}
	public void testGetCourseSteopByName(){
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		assertEquals(parser.getCourseSteopByName("Analysis T1 VU"), GlobalProperties.NO_STEOP);
	}
	public void testGetCourseModeByName(){
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		assertEquals(parser.getCourseModeByName("Analysis T1 VU"), "VU");
	}
	public void testGetCurrentEcts(){
		initParserAndSetInputStream("test_courses.xml");
		parser.parseCourses(false);
		parser.setStatusOfCurrentCourseTo(0, GlobalProperties.STATUS_DONE);
		parser.setStatusOfCurrentCourseTo(1, GlobalProperties.STATUS_DONE);

		assertEquals(parser.getCurrentEcts(),8.0f);
	}
}

/* 
 * public float getCurrentEcts() {
 */
