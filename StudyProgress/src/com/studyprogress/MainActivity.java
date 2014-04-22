package com.studyprogress;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.objects.Course;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private static CourseListAdapter adapterS1;
	private static CourseListAdapter adapterS2;
	private static CourseListAdapter adapterS3;
	private static CourseListAdapter adapterS4;
	private static CourseListAdapter adapterS5;
	private static CourseListAdapter adapterS6;

	private TextView curriculumNameTextField;
	private ListView courseListViewS1;
	private ListView courseListViewS2;
	private ListView courseListViewS3;
	private ListView courseListViewS4;
	private ListView courseListViewS5;
	private ListView courseListViewS6;

	private Button sem1Button;
	private Button sem2Button;
	private Button sem3Button;
	private Button sem4Button;
	private Button sem5Button;
	private Button sem6Button;
	private XMLParser parser;
	private static int curriculumId = 0;

	private static Integer STATUS_DONE = 10;
	private static Integer STATUS_IN_PROGRESS = 11;
	private static Integer STATUS_TO_DO = 12;
	private static int MAX_COURSES = 30;
	public View row;

	private boolean[] isSelected;

	private Map<String, Integer> progressMap = new HashMap<String, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		isSelected = new boolean[MAX_COURSES];

		for (int i = 0; i < MAX_COURSES; i++)
			isSelected[i] = false;

		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		courseListViewS1 = (ListView) findViewById(R.id.courses_list_view_sem1);
		courseListViewS2 = (ListView) findViewById(R.id.courses_list_view_sem2);
		courseListViewS3 = (ListView) findViewById(R.id.courses_list_view_sem3);
		courseListViewS4 = (ListView) findViewById(R.id.courses_list_view_sem4);
		courseListViewS5 = (ListView) findViewById(R.id.courses_list_view_sem5);
		courseListViewS6 = (ListView) findViewById(R.id.courses_list_view_sem6);

		sem1Button = (Button) findViewById(R.id.semester_1_name_button);
		sem2Button = (Button) findViewById(R.id.semester_2_name_button);
		sem3Button = (Button) findViewById(R.id.semester_3_name_button);
		sem4Button = (Button) findViewById(R.id.semester_4_name_button);
		sem5Button = (Button) findViewById(R.id.semester_5_name_button);
		sem6Button = (Button) findViewById(R.id.semester_6_name_button);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				curriculumId = 0;
			} else {
				// TODO: hardcoded
				curriculumId = extras.getInt("Id");
				curriculumNameTextField.setText("" + curriculumId);
			}
		} else {
			if (curriculumId == 0)
				curriculumId = (Integer) savedInstanceState
						.getSerializable("Id");
		}

		initComponents();
	}

	private void initComponents() {
		InputStream is = getResources().openRawResource(R.raw.courses);

		parser = XMLParser.getInstance(is);

		parser.parseCourses();
		parser.initializeCurrentCourses(curriculumId);

		String[] courseNamesS1 = null;
		String[] courseNamesS2 = null;
		String[] courseNamesS3 = null;
		String[] courseNamesS4 = null;
		String[] courseNamesS5 = null;
		String[] courseNamesS6 = null;

		courseNamesS1 = parser.getCourseNamesOfSemester(1);
		courseNamesS2 = parser.getCourseNamesOfSemester(2);
		courseNamesS3 = parser.getCourseNamesOfSemester(3);
		courseNamesS4 = parser.getCourseNamesOfSemester(4);
		courseNamesS5 = parser.getCourseNamesOfSemester(5);
		courseNamesS6 = parser.getCourseNamesOfSemester(6);

		for (int i = 0; i < courseNamesS1.length; i++) {
			progressMap.put(courseNamesS1[i], STATUS_TO_DO);
		}
		adapterS1 = new CourseListAdapter(courseNamesS1, this);
		adapterS2 = new CourseListAdapter(courseNamesS2, this);
		adapterS3 = new CourseListAdapter(courseNamesS3, this);
		adapterS4 = new CourseListAdapter(courseNamesS4, this);
		adapterS5 = new CourseListAdapter(courseNamesS5, this);
		adapterS6 = new CourseListAdapter(courseNamesS6, this);

		// adapter = new ArrayAdapter<String>(this, R.layout.courses_list_item,
		// R.id.courses_text_view, courseNames);

		courseListViewS1.setAdapter(adapterS1);
		courseListViewS2.setAdapter(adapterS2);
		courseListViewS3.setAdapter(adapterS3);
		courseListViewS4.setAdapter(adapterS4);
		courseListViewS5.setAdapter(adapterS5);
		courseListViewS6.setAdapter(adapterS6);

		sem1Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.VISIBLE);
				courseListViewS2.setVisibility(View.INVISIBLE);
				courseListViewS3.setVisibility(View.INVISIBLE);
				courseListViewS4.setVisibility(View.INVISIBLE);
				courseListViewS5.setVisibility(View.INVISIBLE);
				courseListViewS6.setVisibility(View.INVISIBLE);

			}
		});
		sem2Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.INVISIBLE);

				courseListViewS2.setVisibility(View.VISIBLE);
				courseListViewS3.setVisibility(View.INVISIBLE);
				courseListViewS4.setVisibility(View.INVISIBLE);
				courseListViewS5.setVisibility(View.INVISIBLE);
				courseListViewS6.setVisibility(View.INVISIBLE);

			}
		});
		sem3Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.INVISIBLE);
				courseListViewS2.setVisibility(View.INVISIBLE);

				courseListViewS3.setVisibility(View.VISIBLE);
				courseListViewS4.setVisibility(View.INVISIBLE);
				courseListViewS5.setVisibility(View.INVISIBLE);
				courseListViewS6.setVisibility(View.INVISIBLE);

			}
		});
		sem4Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.INVISIBLE);
				courseListViewS2.setVisibility(View.INVISIBLE);
				courseListViewS3.setVisibility(View.INVISIBLE);

				courseListViewS4.setVisibility(View.VISIBLE);
				courseListViewS5.setVisibility(View.INVISIBLE);
				courseListViewS6.setVisibility(View.INVISIBLE);

			}
		});
		sem5Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.INVISIBLE);
				courseListViewS2.setVisibility(View.INVISIBLE);
				courseListViewS3.setVisibility(View.INVISIBLE);
				courseListViewS4.setVisibility(View.INVISIBLE);

				courseListViewS5.setVisibility(View.VISIBLE);
				courseListViewS6.setVisibility(View.INVISIBLE);

			}
		});
		sem6Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				courseListViewS1.setVisibility(View.INVISIBLE);
				courseListViewS2.setVisibility(View.INVISIBLE);
				courseListViewS3.setVisibility(View.INVISIBLE);
				courseListViewS4.setVisibility(View.INVISIBLE);
				courseListViewS5.setVisibility(View.INVISIBLE);

				courseListViewS6.setVisibility(View.VISIBLE);
			}
		});
		// courseListView2.setAdapter(adapter);
		// courseListView3.setAdapter(adapter);

		courseListViewS1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				Log.d("t4", "" + id);
				String item = (String) adapterS1.getItem(position);
				Log.d("t4", "" + item);
				/*
				 * if (isSelected[(int) id] == false){ //v.setSelected(true);
				 * adapter.setViewBackgroundColor(position, Color.GREEN);
				 * isSelected[(int) id] = true; } else{
				 * adapter.setViewBackgroundColor(position, Color.RED);
				 * 
				 * //v.setBackgroundColor(Color.RED); isSelected[(int) id] =
				 * false; //v.setSelected(false);
				 * 
				 * 
				 * }
				 */

				final String courseName = courseListViewS1.getItemAtPosition(
						position).toString();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Select your course process!");

				builder.setPositiveButton("Done",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapterS1.setViewBackgroundColor(position,
										Color.GREEN);
								setProgressOfCourse(courseName, STATUS_DONE);

							}
						});

				builder.setNegativeButton("To Do",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapterS1.setViewBackgroundColor(position,
										Color.RED);
								setProgressOfCourse(courseName, STATUS_TO_DO);

							}
						});

				builder.setNeutralButton("In Progress",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapterS1.setViewBackgroundColor(position,
										Color.YELLOW);
								setProgressOfCourse(courseName,
										STATUS_IN_PROGRESS);
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

				// courseListView.getChildAt(position).setBackgroundColor(Color.GREEN);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private void setProgressOfCourse(String courseName, Integer Progress) {
		progressMap.put(courseName, Progress);
	}

}
