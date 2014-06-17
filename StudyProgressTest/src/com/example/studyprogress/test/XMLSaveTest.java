package com.example.studyprogress.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;

public class XMLSaveTest extends InstrumentationTestCase {
	private InputStream is;
	private XMLParser parser;
	private XMLSave saver;

	protected void setUp() throws Exception {
		super.setUp();
		is = getInstrumentation().getContext().getAssets()
				.open("test_courses.xml");
		parser = XMLParser.getInstance(is);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSaveXML() {
		try {
			is = getInstrumentation().getContext().getAssets()
					.open("test_courses.xml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		parser = XMLParser.getInstance(is);
		parser.parseCourses(false);
		saver = new XMLSave(parser.getCurrentCourses());

		saver.saveXML(	GlobalProperties.SAVE_FILE_NAME, "TestUniversity", "TestCurriculum",
				10, 0);

		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
				GlobalProperties.SAVE_FILE_NAME);

		InputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		parser = XMLParser.getInstance(fileInputStream);
		parser.parseCourses(true);

		assertEquals(parser.getCurrentCurriculum().getName(), "TestCurriculum");
		assertEquals(parser.getCurrentCurriculum().getMode(),
				GlobalProperties.BACH_STUD);
		assertEquals(parser.getCurrentCurriculum().getCurriculumId(), 10);
		assertEquals(parser.getCurrentCourses().get(0).getCourseName(),
				"Analysis T1 VU");
		assertEquals(parser.getCurrentCourses().get(1).getCourseName(),
				"Einführung in das Studium der Informatik VO");

	}

}
