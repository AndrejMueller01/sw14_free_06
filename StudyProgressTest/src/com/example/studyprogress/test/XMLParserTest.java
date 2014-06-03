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

	public void testGetCurriculaNames() {
		
		try {
			is = getInstrumentation().getContext().getAssets().open("test_curricula.xml");
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
	public void testParseUniversities(){
		ArrayList<University> universities= new ArrayList<University>();
		University uni = new University("TU Graz", 1);
		universities.add(uni);
		uni = new University("KFU Graz", 2);
		universities.add(uni);
		
		try {
			is = getInstrumentation().getContext().getAssets().open("test_universities.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.setInputStream(is);
		parser.parseUniversities();
		
		assertEquals(universities, parser.getAllUniversities());
		
	}
	public void testParseCourses(){
		ArrayList<Course> courses= new ArrayList<Course>();
//		setCurrentCurriculum(studName,studMode,studId);
//
//		Course course = new Course("Analysis T1 VU",7,1,"LV");
//		universities.add(uni);
//		uni = new Course("KFU Graz", 2);
//		course.add(uni);
//		
//		try {
//			is = getInstrumentation().getContext().getAssets().open("test_universities.xml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		parser.setInputStream(is);
//		parser.parseUniversities();
//		
//		assertEquals(universities, parser.getAllUniversities());
//		
	}

}
//--------------------------------
/*


	public void parseCourses(boolean isSavedFile) {


	public void setCurrentCurriculum(String studName, int studMode, int studId) {

	public void setCurrentUniversity(String name, int id) {

	// TODO: Variable names
	public void parseCurricula() {


	public int getCurriculumMode(String name) {

	
	public ArrayList<String> getCurriculaNames(InputStream inputStream)
			throws XmlPullParserException, IOException {

	public ArrayList<String> getUniversityNames(InputStream inputStream)

	public int getCurriculumIdWithName(String name) {


	public int getUniversityIdWithName(String name) {

	public ArrayList<Course> getCurrentCourses() {


	public void setStatusOfCurrentCourseTo(int courseId, int status) {


	public String[] getCurrentCoursesNames() throws XmlPullParserException,
			IOException {


	public String[] getCourseNamesOfSemester(int semesterNo) {


	public Map<String, Float> getEctsMapOfAllCurrentCourses() {


	public void deleteCourse(String courseName) {

	
	public float getEctsByName(String courseName)

	
	public String getCourseNumberByName(String courseName)


	public int getCourseSteopByName(String courseName)


	public String getCourseModeByName(String courseName)

	public float getCurrentEcts() {

*/
