package com.studyprogress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;
import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.objects.Course;

import android.os.Bundle;
import android.os.Environment;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ProgressBar studyProgressBar;
	private TextView studyProgressPercentage;

	private static final int SEM_COUNT = 6;
	private static CourseListAdapter[] adapters;

	private TextView curriculumNameTextField;
	private ListView[] courseListViews;

	private Button sem1Button;
	private Button sem2Button;
	private Button sem3Button;
	private Button sem4Button;
	private Button sem5Button;
	private Button sem6Button;

	private float maxEcts;
	private XMLParser parser;
	private boolean firstTimeOpened;

	private static int curriculumId = 0;
	private static String curriculumName = null;

	private static Integer STATUS_DONE = 10;
	private static Integer STATUS_IN_PROGRESS = 11;
	private static Integer STATUS_TO_DO = 12;
	private static int MAX_COURSES_PER_SEM = 30;
	public View row;

	private boolean[] isSelected;

	private Map<String, Float> initialProgressMap;
	private Map<String, Float> currentProgressMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// if (savedInstanceState == null) {
		Bundle extras = getIntent().getExtras();
		firstTimeOpened = extras.getBoolean("firstOpen");
		if (firstTimeOpened) {
			curriculumId = extras.getInt("Id");
			curriculumName = extras.getString("Name");

			// } else {
			// if (curriculumId == 0)
			// curriculumId = (Integer) savedInstanceState
			// .getSerializable("Id");
			// }

			InputStream is = getResources().openRawResource(R.raw.courses);
			parser = XMLParser.getInstance(is);
			parser.parseCourses(false);
			parser.initializeCurrentCourses(curriculumId);

		} else {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/studyprogress_save",
					"my_curriculum.xml");
			Log.d("t4", Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ "/studyprogress_save/my_curriculum.xml");
			InputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fileInputStream == null) {
			}
			parser = XMLParser.getInstance(fileInputStream);
			parser.parseCourses(true);
			parser.initializeAllActualCoursesToCurrentCourses();
		}
		initComponents();
		setMainLayoutTitle(savedInstanceState);

	}

	private void initComponents() {

		isSelected = new boolean[MAX_COURSES_PER_SEM];

		for (int i = 0; i < MAX_COURSES_PER_SEM; i++)
			isSelected[i] = false;

		studyProgressBar = (ProgressBar) findViewById(R.id.study_progress_bar);

		studyProgressPercentage = (TextView) findViewById(R.id.progress_text_view);
		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		curriculumNameTextField.setText(curriculumName + "[Id:" + curriculumId
				+ "]");

		courseListViews = new ListView[SEM_COUNT];
		adapters = new CourseListAdapter[SEM_COUNT];

		courseListViews[0] = (ListView) findViewById(R.id.courses_list_view_sem1);
		courseListViews[1] = (ListView) findViewById(R.id.courses_list_view_sem2);
		courseListViews[2] = (ListView) findViewById(R.id.courses_list_view_sem3);
		courseListViews[3] = (ListView) findViewById(R.id.courses_list_view_sem4);
		courseListViews[4] = (ListView) findViewById(R.id.courses_list_view_sem5);
		courseListViews[5] = (ListView) findViewById(R.id.courses_list_view_sem6);

		sem1Button = (Button) findViewById(R.id.semester_1_name_button);
		sem2Button = (Button) findViewById(R.id.semester_2_name_button);
		sem3Button = (Button) findViewById(R.id.semester_3_name_button);
		sem4Button = (Button) findViewById(R.id.semester_4_name_button);
		sem5Button = (Button) findViewById(R.id.semester_5_name_button);
		sem6Button = (Button) findViewById(R.id.semester_6_name_button);

		initialProgressMap = parser.getEctsMapOfAllCurrentCourses();
		currentProgressMap = parser.getEctsMapOfAllCurrentCourses();
		maxEcts = getAllEctsTodo();
		refreshProgress();

		String[][] courseNames = null;
		courseNames = new String[SEM_COUNT][];

		for (int i = 0; i < SEM_COUNT; i++)
			courseNames[i] = parser.getCourseNamesOfSemester(i + 1);

		for (int i = 0; i < SEM_COUNT; i++)
			adapters[i] = new CourseListAdapter(courseNames[i], this);

		for (int i = 0; i < SEM_COUNT; i++)
			courseListViews[i].setAdapter(adapters[i]);

		setClickListneners();

		if (!firstTimeOpened) {
			setBackgroudColors();
			setCurrentProgressMap();
		}

	}

	private void setCurrentProgressMap() {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getStatus() == 2) {
				currentProgressMap.put(parser.getCurrentCourses().get(i)
						.getCourseName(), (float) 0.0);
			}
			refreshProgress();
		}

	}

	private void setBackgroudColors() {
		for (int j = 0; j < SEM_COUNT; j++)
			for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
				if (parser.getCurrentCourses().get(i).getStatus() == 2) {
					int position = adapters[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						adapters[j].setViewBackgroundColor(position,
								Color.GREEN);
				} else if (parser.getCurrentCourses().get(i).getStatus() == 1) {
					int position = adapters[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						adapters[j].setViewBackgroundColor(position,
								Color.YELLOW);
				} else if (parser.getCurrentCourses().get(i).getStatus() == 0) {

					int position = adapters[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						adapters[j].setViewBackgroundColor(position, Color.RED);
				}
			}
	}

	private void setClickListneners() {

		sem1Button.setOnClickListener(setupOnClickListener(0));
		sem2Button.setOnClickListener(setupOnClickListener(1));
		sem3Button.setOnClickListener(setupOnClickListener(2));
		sem4Button.setOnClickListener(setupOnClickListener(3));
		sem5Button.setOnClickListener(setupOnClickListener(4));
		sem6Button.setOnClickListener(setupOnClickListener(5));

		courseListViews[0].setOnItemClickListener(setupOnItemClickListener(0));
		courseListViews[1].setOnItemClickListener(setupOnItemClickListener(1));
		courseListViews[2].setOnItemClickListener(setupOnItemClickListener(2));
		courseListViews[3].setOnItemClickListener(setupOnItemClickListener(3));
		courseListViews[4].setOnItemClickListener(setupOnItemClickListener(4));
		courseListViews[5].setOnItemClickListener(setupOnItemClickListener(5));

	}

	private void setMainLayoutTitle(Bundle savedInstanceState) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_item:
			for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
				if (currentProgressMap.get(parser.getCurrentCourses().get(i)
						.getCourseName()) == 0.0) {
					parser.setStatusOfCurrentCourseTo(i, 2);
				} else {
					parser.setStatusOfCurrentCourseTo(i, 0);

				}
			}
			XMLSave saver = new XMLSave(parser.getCurrentCourses());
			saver.saveXML();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public OnClickListener setupOnClickListener(final int semester) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("t4", "Button klicked");

				for (int i = 0; i < SEM_COUNT; i++) {
					if (i == semester) {
						Log.d("t4", "VISIBLE");
						courseListViews[i].setVisibility(View.VISIBLE);
					} else
						courseListViews[i].setVisibility(View.INVISIBLE);
					courseListViews[i].refreshDrawableState();
				}
			}
		};
	}

	public OnItemClickListener setupOnItemClickListener(final int semester) {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				final String courseName = courseListViews[semester]
						.getItemAtPosition(position).toString();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Select your course process!");

				builder.setPositiveButton("Done",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapters[semester].setViewBackgroundColor(
										position, Color.GREEN);
								setProgressOfCourseDone(courseName);

							}
						});

				builder.setNegativeButton("To Do",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapters[semester].setViewBackgroundColor(
										position, Color.RED);

								setProgressOfCourseTodo(courseName);

							}
						});

				builder.setNeutralButton("In Progress",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapters[semester].setViewBackgroundColor(
										position, Color.YELLOW);

								setProgressOfCourseTodo(courseName);

							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		};
	}

	public void refreshProgress() {
		float currentEcts = maxEcts - getCurrentEctsTodo();
		studyProgressBar.setMax((int) maxEcts);
		studyProgressBar.setProgress((int) currentEcts);
		studyProgressBar.refreshDrawableState();
		int progressInPercent = 0;
		if (studyProgressBar.getMax() != 0)
			progressInPercent = studyProgressBar.getProgress() * 100
					/ studyProgressBar.getMax();
		studyProgressPercentage.setText(currentEcts + "/" + maxEcts + " ECTS ("
				+ progressInPercent + "%)");
	}

	private float getAllEctsTodo() {
		float allEcts = 0;
		for (float f : initialProgressMap.values()) {
			allEcts += f;
		}
		Log.d("t4", "ECTS_all: " + allEcts);
		return allEcts;
	}

	private float getCurrentEctsTodo() {
		float currentEcts = 0;
		for (float f : currentProgressMap.values()) {
			currentEcts += f;
		}
		Log.d("t4", "ECTS_done: " + currentEcts);

		return currentEcts;
	}

	private void setProgressOfCourseDone(String courseName) {
		if (initialProgressMap.containsKey(courseName)) {
			currentProgressMap.put(courseName, (float) 0.0);
		}

		refreshProgress();

	}

	private void setProgressOfCourseTodo(String courseName) {
		if (initialProgressMap.containsKey(courseName)) {
			currentProgressMap.put(courseName,
					initialProgressMap.get(courseName));
		}
		refreshProgress();

	}

}
