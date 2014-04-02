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
	private ArrayList<Course> courses = null;
	private InputStream inputStream;

	public XMLParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void parseCourses() {
		int eventType = 0;
		try {

			xmlPullParseFactory = XmlPullParserFactory.newInstance();
			xmlPullParseFactory.setNamespaceAware(true);

			xmlPullParser = xmlPullParseFactory.newPullParser();
			xmlPullParser.setInput(inputStream, null);
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

			eventType = xmlPullParser.getEventType();
			Course currentCourse = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					courses = new ArrayList<Course>();
					break;
				case XmlPullParser.START_TAG:

					name = xmlPullParser.getName();

					if (name.equals("course")) {

						currentCourse = new Course();
					} else if (currentCourse != null) {
						if (name.equals("name")) {
							currentCourse.setCourseName(xmlPullParser.nextText());
						}
						if (name.equals("id")) {
							currentCourse.setCurricula(Integer.parseInt(xmlPullParser
									.nextText()));
						}
						// TODO: more
					}
					break;
				case XmlPullParser.END_TAG:

					name = xmlPullParser.getName();
					if (name.equals("course") && currentCourse != null) {
						courses.add(currentCourse);
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
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
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
			Log.d("SIZE", curricula.get(i).getName());
			curriculaNames.add(curricula.get(i).getName());
		}

		return curriculaNames;
	}

	public String[] getCoursesNames(InputStream inputStream)
			throws XmlPullParserException, IOException {

		String[] coursesNames = new String[courses.size()];

		for (int i = 0; i < courses.size(); i++) {
			Log.d("SIZE", courses.get(i).getCourseName());
			coursesNames[i] = courses.get(i).getCourseName();
		}

		return coursesNames;
	}

}