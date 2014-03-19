package com.example.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.studyprogress.Curriculum;

import android.util.Log;

public class XMLParser {
	// Returns the entire XML document
	public String[] getCurriculaNames(InputStream inputStream)
			throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(inputStream, null);
		xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		ArrayList<Curriculum> curricula = null;
		int eventType = xpp.getEventType();
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
				} 
				else if (currentCurriculum != null) {
					if (name.equals("name")) {
						currentCurriculum.setName(xpp.nextText());
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
		String[] curriculaNames = new String[curricula.size()];

		for (int i = 0; i < curricula.size(); i++) {
			curriculaNames[i] = curricula.get(i).getName();
		}

		return curriculaNames;
	}
}