package com.studyprogress.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;
import android.util.Xml;

import com.studyprogress.objects.Course;

public class XMLSave {
	private String rootDir = Environment.getExternalStorageDirectory().toString();
	private File appDir = new File(rootDir + "/studyprogress_save");
	private ArrayList<Course> courseList;

	public XMLSave( ArrayList<Course> currentCourses) {
		appDir.mkdirs();
		courseList = currentCourses;
	}

	public void saveXML(String curriculumName, int curriculumId, int studMode) {

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

			serializer.startTag(null, "courses");
			serializer.attribute(null, "cname", curriculumName);
			serializer.attribute(null, "cid", ""+curriculumId);
			serializer.attribute(null, "cisDiplSt", ""+studMode);

			for (int j = 0; j < courseList.size(); j++) {
				//TODO: more informations
				serializer.startTag(null, "course");
				serializer.startTag(null, "name");
				serializer.text(""+courseList.get(j).getCourseName());
				serializer.endTag(null, "name");
				serializer.startTag(null, "id");
				serializer.text(""+courseList.get(j).getCurriculaNumber());
				serializer.endTag(null, "id");
				serializer.startTag(null, "ects");
				serializer.text(""+courseList.get(j).getEcts());
				serializer.endTag(null, "ects");
				serializer.startTag(null, "semester");
				serializer.text(""+courseList.get(j).getSemester());
				serializer.endTag(null, "semester");
				serializer.startTag(null, "bachelor");
				serializer.endTag(null, "bachelor");
				serializer.startTag(null, "cid");
				//serializer.text(""+courseList.get(j).getCourseNumber());
				serializer.endTag(null, "cid");
				serializer.startTag(null, "steop");
				serializer.text(""+courseList.get(j).getSteop());
				serializer.endTag(null, "steop");
				serializer.startTag(null, "mode");
				serializer.endTag(null, "mode");
				serializer.startTag(null, "status");
				serializer.text(""+courseList.get(j).getStatus());
				serializer.endTag(null, "status");
				serializer.endTag(null, "course");

			}
			serializer.endTag(null, "courses");
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
