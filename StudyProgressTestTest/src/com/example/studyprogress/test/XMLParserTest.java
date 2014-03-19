package com.example.studyprogress.test;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.studyprogress.R;
import com.example.tools.XMLParser;


public class XMLParserTest extends InstrumentationTestCase {
	XMLParser mClassToTest;

	protected void setUp() throws Exception {
		mClassToTest = new XMLParser();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurriculaNames() {
		InputStream is = getInstrumentation().getTargetContext().getResources()
				.openRawResource(R.raw.test_curricula);
		String[] testCurricula = { "TestCurriculum1", "TestCurriculum2",
				"TestCurriculum3", "TestCurriculum4", "TestCurriculum5" };
		String[] actualCurricula;
		try {
			actualCurricula = mClassToTest.getCurriculaNames(is);
			for (int i = 0; i < 5; i++)
				Log.d("TC1", testCurricula[i] + " = " + actualCurricula[i]);
			assertEquals(testCurricula[0], actualCurricula[0]);
			assertEquals(testCurricula[1], actualCurricula[1]);
			assertEquals(testCurricula[2], actualCurricula[2]);
			assertEquals(testCurricula[3], actualCurricula[3]);
			assertEquals(testCurricula[4], actualCurricula[4]);
			Log.d("TC1", "FINISHED");

		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
