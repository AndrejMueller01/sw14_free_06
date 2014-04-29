package com.studyprogress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;
import com.studyprogress.adapter.CourseListAdapter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressBar studyProgressBar;
	private TextView studyProgressPercentage;

	private static final int SEM_COUNT = 7;

	private static CourseListAdapter[] adapters;

	private TextView curriculumNameTextField;
	private TextView semesterTextField;

	private ListView[] courseListViews;

	private Button sem1Button;
	private Button sem2Button;
	private Button sem3Button;
	private Button sem4Button;
	private Button sem5Button;
	private Button sem6Button;
	private Button semOptCourses;
	
	private float maxEcts;
	private XMLParser parser;
	private boolean firstTimeOpened;

	private static int curriculumId = 0;
	private static int isDiplSt = 0;
	private static String curriculumName = null;

	private static Integer STATUS_DONE = 2;
	private static Integer STATUS_IN_PROGRESS = 1;
	private static Integer STATUS_TO_DO = 0;
	public View row;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extras = getIntent().getExtras();
		firstTimeOpened = extras.getBoolean("firstOpen");
		boolean fromCreateNew = extras.getBoolean("fromCreateNew");


		if (firstTimeOpened) {
			curriculumId = extras.getInt("Id");
			curriculumName = extras.getString("Name");
			isDiplSt = extras.getInt("IsDiplSt");
			InputStream is = getResources().openRawResource(R.raw.courses);
			parser = XMLParser.getInstance(is);
			parser.parseCourses(false);
			parser.initializeCurrentCourses(curriculumId);

		} else if(fromCreateNew){
			parser = XMLParser.getInstance(null);

			// Already opened
		}
		else if(!firstTimeOpened){
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/studyprogress_save",
					"my_curriculum.xml");

			InputStream fileInputStream = null;

			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Toast.makeText(getBaseContext(), R.string.file_not_found, Toast.LENGTH_LONG).show();
				return;
			}

			parser = XMLParser.getInstance(fileInputStream);
			parser.parseCourses(true);
			parser.initializeAllActualCoursesToCurrentCourses();
			
			curriculumId = parser.getCurrentCurriculum().getCurriculumId();
			curriculumName = parser.getCurrentCurriculum().getName();
			isDiplSt = parser.getCurrentCurriculum().getDiplSt();
		}

		initComponents();

	}

	private void initComponents() {

		studyProgressBar = (ProgressBar) findViewById(R.id.study_progress_bar);

		studyProgressPercentage = (TextView) findViewById(R.id.progress_text_view);
		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		curriculumNameTextField.setText(curriculumName + "[Id:" + curriculumId
				+ "]");

		courseListViews = new ListView[SEM_COUNT];
		adapters = new CourseListAdapter[SEM_COUNT];
		semesterTextField = (TextView) findViewById(R.id.semester_line_description_text_view);
		courseListViews[0] = (ListView) findViewById(R.id.courses_list_view_sem1);
		courseListViews[1] = (ListView) findViewById(R.id.courses_list_view_sem2);
		courseListViews[2] = (ListView) findViewById(R.id.courses_list_view_sem3);
		courseListViews[3] = (ListView) findViewById(R.id.courses_list_view_sem4);
		courseListViews[4] = (ListView) findViewById(R.id.courses_list_view_sem5);
		courseListViews[5] = (ListView) findViewById(R.id.courses_list_view_sem6);
		
		courseListViews[6] = (ListView) findViewById(R.id.courses_list_view_opt_courses);


		sem1Button = (Button) findViewById(R.id.semester_1_name_button);
		sem2Button = (Button) findViewById(R.id.semester_2_name_button);
		sem3Button = (Button) findViewById(R.id.semester_3_name_button);
		sem4Button = (Button) findViewById(R.id.semester_4_name_button);
		sem5Button = (Button) findViewById(R.id.semester_5_name_button);
		sem6Button = (Button) findViewById(R.id.semester_6_name_button);
		semOptCourses = (Button) findViewById(R.id.semester_optional_courses);
		
		if(isDiplSt == 1){
			Log.d("t4", "isDiplST");
			sem4Button.setVisibility(View.INVISIBLE);
			sem5Button.setVisibility(View.INVISIBLE);
			sem6Button.setVisibility(View.INVISIBLE);
			semesterTextField.setText("Abschnitt");
		}

		maxEcts = getAllEcts();
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
		}

	}

	private void setBackgroudColors() {
		for (int j = 0; j < SEM_COUNT; j++)
			for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
				if (parser.getCurrentCourses().get(i).getStatus() == STATUS_DONE) {
					int position = adapters[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						adapters[j].setViewBackgroundColor(position,
								Color.GREEN);
				} else if (parser.getCurrentCourses().get(i).getStatus() == STATUS_IN_PROGRESS) {
					int position = adapters[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						adapters[j].setViewBackgroundColor(position,
								Color.YELLOW);
				} else if (parser.getCurrentCourses().get(i).getStatus() == STATUS_TO_DO) {

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
		semOptCourses.setOnClickListener(setupOnClickListener(6));

		for (int i = 0; i < SEM_COUNT; i++)
			courseListViews[i]
					.setOnItemClickListener(setupOnItemClickListener(i));

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
			XMLSave saver = new XMLSave(parser.getCurrentCourses());
			saver.saveXML(curriculumName, curriculumId, isDiplSt);
			Toast.makeText(getBaseContext(), R.string.save_text_succ, Toast.LENGTH_SHORT).show();
			return true;
			
		case R.id.add_item:
			Intent intent = new Intent(MainActivity.this,
					CreateOptionalCourses.class);
			startActivity(intent);

			
			
			
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
				builder.setTitle("Wähle deinen Fortschritt!");

				builder.setPositiveButton("Geschafft",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapters[semester].setViewBackgroundColor(
										position, Color.GREEN);
								setProgressOfCourseDone(courseName);

							}
						});

				builder.setNegativeButton("zu machen",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								adapters[semester].setViewBackgroundColor(
										position, Color.RED);

								setProgressOfCourseTodo(courseName);

							}
						});

				builder.setNeutralButton("In Arbeit",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								adapters[semester].setViewBackgroundColor(
										position, Color.YELLOW);

								setProgressOfCourseInProgress(courseName);
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		};
	}

	

	public void refreshProgress() {
		float currentEcts = getCurrentEcts();
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

	private float getAllEcts() {
		float allEcts = 0;
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
				allEcts += parser.getCurrentCourses().get(i).getEcts();
		}
		return allEcts;
	}

	private float getCurrentEcts() {
		float currentEcts = 0;
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if ((parser.getCurrentCourses().get(i).getStatus() == STATUS_DONE))
				currentEcts += parser.getCurrentCourses().get(i).getEcts();
		}
		return currentEcts;
	}

	private void setProgressOfCourseDone(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i, STATUS_DONE);
		}

		refreshProgress();

	}

	private void setProgressOfCourseTodo(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i, STATUS_TO_DO);
		}

		refreshProgress();

	}

	private void setProgressOfCourseInProgress(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i, STATUS_IN_PROGRESS);
		}

		refreshProgress();

	}
}
