package com.example.studyprogress.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;

public class XMLParserTest extends InstrumentationTestCase {
	XMLParser mClassToTest;

	protected void setUp() throws Exception {
		super.setUp();

		InputStream is = getInstrumentation().getTargetContext().getResources()
				.openRawResource(R.raw.test_curricula);
		mClassToTest = new XMLParser(is);
		mClassToTest.parseCurricula();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurriculaNames() {
		InputStream is = getInstrumentation().getTargetContext().getResources()
				.openRawResource(R.raw.test_curricula);
		ArrayList<String> testCurricula = new ArrayList<String>();
		
		testCurricula.add("TestCurriculum1");
		testCurricula.add("TestCurriculum2");
		testCurricula.add("TestCurriculum3");
		testCurricula.add("TestCurriculum4");
		testCurricula.add("TestCurriculum5");

		ArrayList<String> actualCurricula;
		
		try {
			actualCurricula = mClassToTest.getCurriculaNames(is);

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
}
