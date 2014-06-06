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
	private void openAndParseXMLFile(String fileName){
		
	}
	public void testGetCurriculaNames() {
		parser = new XMLParser(null);
		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_curricula.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
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
		parser = new XMLParser(null);

		ArrayList<University> universities = new ArrayList<University>();
		University uni = new University("TU Graz", 1);
		universities.add(uni);
		uni = new University("KFU Graz", 2);
		universities.add(uni);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_universities.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseUniversities();

		assertEquals(universities, parser.getAllUniversities());

	}

	public void testParseCourses() {
		parser = new XMLParser(null);

		ArrayList<Course> courses = new ArrayList<Course>();
		//
		Course course = new Course("Analysis T1 VU", 7, 1, "VU");
		courses.add(course);
		course = new Course("Einführung in das Studium der Informatik VO", 1,
				1, "VO");
		courses.add(course);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_courses.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		parser.setInputStream(is);
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
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_courses_saved.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseCourses(true);
		assertEquals("Informatik", parser.getCurrentCurriculum().getName());

	}

	public void testParseCurricula() {
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_curricula.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseCurricula();
		for (int i = 0; i < parser.getCurricula().size(); i++)
			assertEquals("TestCurriculum" + (i + 1),
					parser.getCurricula().get(i).getName());

	}

	public void testGetCurriculumMode() {
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_curricula.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseCurricula();
		assertEquals(parser.getCurriculumMode("TestCurriculum1"), 2);

	}

	public void testGetUniversityNames() {
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_universities.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
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
	public void testGetCurriculumIdWithName(){
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_curricula.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseCurricula();

		
		assertEquals("1",parser.getCurriculumIdWithName("TestCurriculum1"));
	}
	public void testGetUniversityIdWithName(){
		parser = new XMLParser(null);

		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_univeristies.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseCurricula();

		
		assertEquals("1",parser.getUniversityIdWithName("TestCurriculum1"));
	}
}
// --------------------------------
/*
 * 
 * 
 * 
 * public int getUniversityIdWithName(String name) {
 * 
 * public ArrayList<Course> getCurrentCourses() {
 * 
 * 
 * public void setStatusOfCurrentCourseTo(int courseId, int status) {
 * 
 * 
 * public String[] getCurrentCoursesNames() throws XmlPullParserException,
 * IOException {
 * 
 * 
 * public String[] getCourseNamesOfSemester(int semesterNo) {
 * 
 * 
 * public Map<String, Float> getEctsMapOfAllCurrentCourses() {
 * 
 * 
 * public void deleteCourse(String courseName) {
 * 
 * 
 * public float getEctsByName(String courseName)
 * 
 * 
 * public String getCourseNumberByName(String courseName)
 * 
 * 
 * public int getCourseSteopByName(String courseName)
 * 
 * 
 * public String getCourseModeByName(String courseName)
 * 
 * public float getCurrentEcts() {
 */
