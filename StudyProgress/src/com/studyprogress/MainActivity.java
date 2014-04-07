package com.studyprogress;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private static ArrayAdapter<String> adapter;
	private TextView curriculumNameTextField;
	private ListView courseListView;
	private XMLParser parser;
	private int curriculumId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		courseListView = (ListView) findViewById(R.id.courses_list_view);


		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				curriculumId = 0;
			} else {
				// TODO: hardcoded
				curriculumId = extras.getInt("Id");
				curriculumNameTextField.setText(""+curriculumId);
			}
		} else {
			curriculumId = (Integer) savedInstanceState.getSerializable("Id");
		}

		initComponents();
	}

	private void initComponents() {
		InputStream is = getResources().openRawResource(R.raw.courses);

		parser = XMLParser.getInstance(is);

		parser.parseCourses();
		parser.initializeCurrentCourses(curriculumId);
		String[] courseNames = null;

		try {
			courseNames = parser.getCurrentCoursesNames(is);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new ArrayAdapter<String>(this, R.layout.courses_list_item,
				R.id.courses_text_view, courseNames);

		courseListView.setAdapter(adapter);
		// updateListViewOnSearching();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

}
