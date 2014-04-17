package com.studyprogress.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.studyprogress.objects.Course;
import com.studyprogress.objects.Curriculum;

import android.util.Log;

public class XMLParser {

	private XmlPullParserFactory xmlPullParseFactory;
	private XmlPullParser xmlPullParser;
	private ArrayList<Curriculum> curricula = null;
	private ArrayList<Course> allCourses = null;
	private ArrayList<Course> currentCourses  = null;
	private InputStream inputStream;
	private static XMLParser instance = null;

	public XMLParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public static XMLParser getInstance(InputStream is) {
		if (instance == null) {
			instance = new XMLParser(is);
		} else {
			instance.setInputStream(is);
		}
		return instance;
	}

	public void setInputStream(InputStream is) {
		this.inputStream = is;
	}

	public void parseCourses() {
		int eventType = 0;
		try {

			xmlPullParseFactory = XmlPullParserFactory.newInstance();
			xmlPullParseFactory.setNamespaceAware(true);

			xmlPullParser = xmlPullParseFactory.newPullParser();
			xmlPullParser.setInput(inputStream, null);
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
					false);

			eventType = xmlPullParser.getEventType();
			Course currentCourse = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					allCourses = new ArrayList<Course>();
					break;
				case XmlPullParser.START_TAG:

					name = xmlPullParser.getName();

					if (name.equals("course")) {

						currentCourse = new Course();
					} else if (currentCourse != null) {
						if (name.equals("name")) {
							currentCourse.setCourseName(xmlPullParser
									.nextText());
						}
						if (name.equals("id")) {
							currentCourse.setCurricula(Integer
									.parseInt(xmlPullParser.nextText()));
						}
						// TODO: more
					}
					break;
				case XmlPullParser.END_TAG:

					name = xmlPullParser.getName();
					if (name.equals("course") && currentCourse != null) {
						allCourses.add(currentCourse);
						currentCourse = null;
					}
				}
				eventType = xmlPullParser.next();

			}

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// TODO: Variable names
	public void parseCurricula() {
		int eventType = 0;
		try {

			xmlPullParseFactory = XmlPullParserFactory.newInstance();
			xmlPullParseFactory.setNamespaceAware(true);
			xmlPullParser = xmlPullParseFactory.newPullParser();
			xmlPullParser.setInput(inputStream, null);
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
					false);
			eventType = xmlPullParser.getEventType();

			Curriculum currentCurriculum = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					curricula = new ArrayList<Curriculum>();
					break;
				case XmlPullParser.START_TAG:
					name = xmlPullParser.getName();

					if (name.equals("curriculum")) {
						currentCurriculum = new Curriculum();
					} else if (currentCurriculum != null) {
						if (name.equals("name")) {
							currentCurriculum.setName(xmlPullParser.nextText());
						}
						if (name.equals("id")) {
							// TODO: test with no int
							currentCurriculum.setCurriculumId(Integer
									.parseInt(xmlPullParser.nextText()));
						}
						// TODO: more
					}
					break;
				case XmlPullParser.END_TAG:

					name = xmlPullParser.getName();
					if (name.equals("curriculum") && currentCurriculum != null) {
						curricula.add(currentCurriculum);
						currentCurriculum = null;
					}
				}
				eventType = xmlPullParser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> getCurriculaNames(InputStream inputStream)
			throws XmlPullParserException, IOException {
		ArrayList<String> curriculaNames = new ArrayList<String>();

		for (int i = 0; i < curricula.size(); i++) {
			curriculaNames.add(curricula.get(i).getName());
		}
		return curriculaNames;
	}



	public int getCurriculumIdWithName(String name) {
		for (int i = 0; i < curricula.size(); i++)
			if (curricula.get(i).getName().equals(name)) {
				return curricula.get(i).getCurriculumId();
			}
		return 0;
	}	

	public void initializeCurrentCourses(int id) {
		// TODO: loading
		currentCourses = new ArrayList<Course>();
		for (int i = 0; i < allCourses.size(); i++)
			if (allCourses.get(i).getCurricula() == id) {
				currentCourses.add(allCourses.get(i));
			}
	}
	
	public ArrayList<Course> getCurrentCourses()
	{
		return currentCourses;
	}

	public String[] getCurrentCoursesNames(InputStream inputStream)
			throws XmlPullParserException, IOException {

		String[] coursesNames = new String[currentCourses.size()];

		for (int i = 0; i < currentCourses.size(); i++) {
			Log.d("SIZE", currentCourses.get(i).getCourseName());
			coursesNames[i] = currentCourses.get(i).getCourseName();
		}

		return coursesNames;
	}

}