package com.example.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.objects.Course;
import com.example.objects.Curriculum;

import android.util.Log;

public class XMLParser {
	// Returns the entire XML document
	private XmlPullParserFactory factory;
	private XmlPullParser xpp;
	ArrayList<Curriculum> curricula = null;
	ArrayList<Course> courses = null;

	
	
	public XMLParser(InputStream inputStream){
		parseCurricula(inputStream);
		parseCourses(inputStream);
	}


	private void parseCourses(InputStream inputStream) 
	{
		int eventType = 0;
		try {
			
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(inputStream, null);
			xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			eventType = xpp.getEventType();

			Course currentCourse = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				Log.d("Parser", "while enter!");
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					courses = new ArrayList<Course>();
					break;
				case XmlPullParser.START_TAG:
					name = xpp.getName();
					Log.d("Parser", name);
					if (name.equals("course")) {
						currentCourse = new Course();
					} else if (currentCourse != null) {
						if (name.equals("name")) {
							currentCourse.setCourseName(xpp.nextText());
						}
						if (name.equals("id")) {
							// TODO: test with no int
							currentCourse.setCurricula(Integer.parseInt(xpp.nextText()));
						}
						// TODO: more
					}
					break;
				case XmlPullParser.END_TAG:

					name = xpp.getName();
					if (name.equals("course") && currentCourse != null) {
						courses.add(currentCourse);
						currentCourse = null;
					}
				}
				eventType = xpp.next();
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
	private void parseCurricula(InputStream inputStream) {
		int eventType = 0;
		try {
			
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(inputStream, null);
			xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			eventType = xpp.getEventType();

			Curriculum currentCurriculum = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {

				Log.d("Parser", "while enter!");
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					curricula = new ArrayList<Curriculum>();
					break;
				case XmlPullParser.START_TAG:
					name = xpp.getName();
					Log.d("Parser", name);
					if (name.equals("curriculum")) {
						currentCurriculum = new Curriculum();
					} else if (currentCurriculum != null) {
						if (name.equals("name")) {
							currentCurriculum.setName(xpp.nextText());
						}
						if (name.equals("id")) {
							// TODO: test with no int
							currentCurriculum.setCurriculumId(Integer.parseInt(xpp.nextText()));
						}
						// TODO: more
					}
					break;
				case XmlPullParser.END_TAG:

					name = xpp.getName();
					if (name.equals("curriculum") && currentCurriculum != null) {
						curricula.add(currentCurriculum);
						currentCurriculum = null;
					}
				}
				eventType = xpp.next();
			}
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String[] getCurriculaNames(InputStream inputStream)
			throws XmlPullParserException, IOException {

		
		String[] curriculaNames = new String[curricula.size()];
		if(curriculaNames.length == 0)
			return null;
		
		for (int i = 0; i < curricula.size(); i++) {
			Log.d("SIZE", curricula.get(i).getName());
			curriculaNames[i] = curricula.get(i).getName();
		}

		return curriculaNames;
	}

	public String[] getCoursesNames(InputStream inputStream)
			throws XmlPullParserException, IOException {

		
		String[] coursesNames = new String[courses.size()];
		if(coursesNames.length == 0)
			return null;
		
		for (int i = 0; i < courses.size(); i++) {
			Log.d("SIZE", courses.get(i).getCourseName());
			coursesNames[i] = courses.get(i).getCourseName();
		}

		return coursesNames;
	}	

}