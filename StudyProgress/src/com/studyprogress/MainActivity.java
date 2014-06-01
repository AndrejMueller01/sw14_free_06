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
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

	private static boolean studyStateChanged = false;

	private float maxEcts;
	private XMLParser parser;
	private int firstTimeOpened;

	private static int curriculumId = 0;
	private static int universityId = 0;

	private static int studMode = 0;

	private static String curriculumName = null;

	private static Integer STATUS_DONE = 2;
	private static Integer STATUS_IN_PROGRESS = 1;
	private static Integer STATUS_TO_DO = 0;

	private static Integer FIRST_TIME = 1;
	private static Integer NOT_FIRST_TIME = 0;

	private static Integer DIPL_STUD = 1;
	private static Integer BACH_STUD = 0;
	private static Integer MAST_STUD = 2;
	private static Integer PHD_STUD = 3;
	private static Integer LA_STUD = 4;

	public View row;
	private boolean onDelButtonFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extras = getIntent().getExtras();
		firstTimeOpened = extras.getInt("firstOpen");
		parser = XMLParser.getInstance(null);

		if (firstTimeOpened == FIRST_TIME) {

			curriculumId = parser.getCurrentCurriculum().getCurriculumId();
			universityId = parser.getCurrentUniversity().getId();

			curriculumName = parser.getCurrentCurriculum().getName();
			studMode = parser.getCurrentCurriculum().getMode();

			InputStream is = null;
			try {
				is = getResources().openRawResource(
						getResources().getIdentifier("c" + universityId+"_"+curriculumId , "raw",
								getPackageName()));

				parser = XMLParser.getInstance(is);
				parser.parseCourses(false);
			} catch (NotFoundException ex) {
				//TODO:error handling

			}
		}

		else if (firstTimeOpened == NOT_FIRST_TIME) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/studyprogress_save",
					"my_curriculum.xml");

			InputStream fileInputStream = null;

			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Toast.makeText(getBaseContext(), R.string.file_not_found,
						Toast.LENGTH_LONG).show();
				return;
			}

			parser = XMLParser.getInstance(fileInputStream);
			parser.parseCourses(true);
			// parser.initializeAllActualCoursesToCurrentCourses();

			curriculumId = parser.getCurrentCurriculum().getCurriculumId();
			curriculumName = parser.getCurrentCurriculum().getName();
			studMode = parser.getCurrentCurriculum().getMode();
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

		if (studMode == DIPL_STUD) {

			sem4Button.setVisibility(View.INVISIBLE);
			sem5Button.setVisibility(View.INVISIBLE);
			sem6Button.setVisibility(View.INVISIBLE);
			semesterTextField.setText("Abschnitt");
		} else if (studMode == MAST_STUD) {
			sem5Button.setVisibility(View.INVISIBLE);
			sem6Button.setVisibility(View.INVISIBLE);
		} else if (studMode == LA_STUD) {
			// TODO:+3 Sem Buttons
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
		setBackgroudColors();

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

		for (int i = 0; i < SEM_COUNT; i++) {
			courseListViews[i]
					.setOnItemClickListener(setupOnItemClickListener(i));
		}

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
			saveCourse();
			return true;
		case R.id.delete_item:

			if (onDelButtonFlag) {
				for (int i = 0; i < SEM_COUNT; i++) {
					courseListViews[i]
							.setOnItemClickListener(setupOnDeleteOptionSelectedClickListener(i + 1));
					adapters[i].setDelMode(true);
				}
				item.getIcon().setAlpha(125);
				onDelButtonFlag = false;
			} else {
				for (int i = 0; i < SEM_COUNT; i++) {
					courseListViews[i]
							.setOnItemClickListener(setupOnItemClickListener(i));
					adapters[i].setDelMode(false);

				}
				item.getIcon().setAlpha(255);

				onDelButtonFlag = true;
			}

			return true;
		case R.id.add_item:
			Intent intent = new Intent(MainActivity.this,
					CreateOptionalCourses.class);
			startActivity(intent);
			studyStateChanged = true;
			finish();
			return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}

	public OnItemClickListener setupOnDeleteOptionSelectedClickListener(
			final int semester) {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				final String courseName = courseListViews[semester - 1]
						.getItemAtPosition(position).toString();
				parser.deleteCourse(courseName);
				String[] courseNames = null;
				courseNames = parser.getCourseNamesOfSemester(semester);
				adapters[semester - 1].setCourseNames(courseNames, position);
				// adapters[semester - 1] = new CourseListAdapter(courseNames,
				// view.getContext());
				// courseListViews[semester - 1]
				// .setAdapter(adapters[semester - 1]);

			}
		};
	}

	private void saveCourse() {
		studyStateChanged = false;
		XMLSave saver = new XMLSave(parser.getCurrentCourses());
		saver.saveXML(curriculumName, curriculumId, studMode);
		Toast.makeText(getBaseContext(), R.string.save_text_succ,
				Toast.LENGTH_SHORT).show();
	}

	public OnClickListener setupOnClickListener(final int semester) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {

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
				studyStateChanged = true;
				final String courseName = courseListViews[semester]
						.getItemAtPosition(position).toString();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				//build title with course information
				builder.setTitle(courseName);
				//builder.setTitle(R.string.choose_progress);
				String courseInformation = "ECTS: ";
				float courseEcts = parser.getEctsByName(courseName);
				String ectsString = Float.valueOf(courseEcts).toString();
				courseInformation += ectsString +"\n";
				courseInformation += "Kursnummer: ";
				courseInformation += parser.getCourseNumberByName(courseName);
				courseInformation += "\n";
				courseInformation += "Steop: ";
				int courseSteop = parser.getCourseSteopByName(courseName);
				if(courseSteop == 1)
				{
					courseInformation += "Ja\n";					
				}
				else courseInformation += "Nein\n";		
				
				courseInformation += "Modus: ";
				courseInformation += parser.getCourseModeByName(courseName);
				courseInformation += "\n";
				
				
				builder.setMessage(courseInformation);
				builder.setPositiveButton(R.string.done,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								adapters[semester].setViewBackgroundColor(
										position, Color.GREEN);
								setProgressOfCourseDone(courseName);

							}
						});

				builder.setNegativeButton(R.string.todo,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								adapters[semester].setViewBackgroundColor(
										position, Color.RED);

								setProgressOfCourseTodo(courseName);

							}
						});

				builder.setNeutralButton(R.string.inprogress,
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
			// percentage calculation
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

	@Override
	public void onBackPressed() {
		if (studyStateChanged) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.changes_save)
					.setCancelable(false)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									saveCourse();
									MainActivity.this.finish();
								}
							})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									MainActivity.this.finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			super.onBackPressed();
		}
	}

}
