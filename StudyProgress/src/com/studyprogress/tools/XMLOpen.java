package com.studyprogress.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.studyprogress.objects.Course;

public class XMLOpen {
	private String rootDir = Environment.getExternalStorageDirectory().toString();
	private File appDir = new File(rootDir + "/studyprogress_save");
	private Map<String,Float> courseMap;
	private ArrayList<Course> courseList;

	public XMLOpen(Map<String,Float> currentCoursesMap, ArrayList<Course> currentCourses) {
		appDir.mkdirs();
		courseMap = currentCoursesMap;
		courseList = currentCourses;
	}

	public void saveXML() {

		try {
			File file = new File(appDir, "my_curriculum.xml");
			if (file.exists())
				file.delete();

			FileOutputStream out = new FileOutputStream(file);

			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(out, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);

			serializer.startTag(null, "root");

			for (int j = 0; j < courseMap.size(); j++) {
				Log.d("t4", "save");
				serializer.startTag(null, "name");
				serializer.text(courseList.get(j).getCourseName());
				serializer.endTag(null, "name");
				serializer.startTag(null, "ectsNotDone");
				serializer.text(""+courseMap.get(courseList.get(j).getCourseName()));
				serializer.endTag(null, "ectsNotDone");
			}
			serializer.endDocument();

			serializer.flush();

			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
