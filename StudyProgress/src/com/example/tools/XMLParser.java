package com.example.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.objects.Curriculum;

import android.util.Log;

public class XMLParser {

	private XmlPullParserFactory factory;
	private XmlPullParser xpp;
	ArrayList<Curriculum> curricula = null;
	
	
	public XMLParser(InputStream inputStream){
		parseCurricula(inputStream);
	}

// TODO: Variable names, hc values
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
			e.printStackTrace();
		} catch (IOException e) {
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


}