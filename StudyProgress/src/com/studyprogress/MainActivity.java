package com.studyprogress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.studyprogress.R;
import com.example.studyprogress.R.layout;
import com.studyprogress.menu.DeleteMenuCallback;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.ProgressCalculator;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;
import com.studyprogress.adapter.CourseListAdapter;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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

	private static CourseListAdapter[] adapters;

	private TextView curriculumNameTextField;
	private TextView semesterTextField;

	private static ListView[] courseListViews;

	private ArrayList<Button> semesterButtons;

	private static boolean studyStateChanged = false;

	private XMLParser parser;
	private int firstTimeOpened;

	private static int curriculumId = 0;
	private static int universityId = 0;

	private static int studMode = 0;

	private static String curriculumName = null;
	// TODO: for a later version
	//private static String universityName = null;


	private static Integer FIRST_TIME = 1;
	private static Integer NOT_FIRST_TIME = 0;

	public View row;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBarProperties.standardMainActivityMenu(this);
		
		startConfiguration();
		initComponents();

	}
	private void startConfiguration(){
		Bundle extras = getIntent().getExtras();
		if (extras.containsKey("firstOpen"))
			firstTimeOpened = extras.getInt("firstOpen");
		if (extras.containsKey("changed"))
			studyStateChanged = extras.getBoolean("changed");

		parser = XMLParser.getInstance(null);

		if (firstTimeOpened == FIRST_TIME) {

			curriculumId = parser.getCurrentCurriculum().getCurriculumId();
			universityId = parser.getCurrentUniversity().getId();

			curriculumName = parser.getCurrentCurriculum().getName();
			//universityName = parser.getCurrentUniversity().getName();
			studMode = parser.getCurrentCurriculum().getMode();

			InputStream is = null;
			parser.clearCurrentCourses();

			try {
				is = getResources().openRawResource(
						getResources().getIdentifier(
								GlobalProperties.COURSE_XML_PREFIX
										+ universityId + "_" + curriculumId,
								"raw", getPackageName()));

				parser = XMLParser.getInstance(is);
				parser.parseCourses(false);
			} catch (NotFoundException ex) {

			}
		}

		else if (firstTimeOpened == NOT_FIRST_TIME) {
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

			curriculumId = parser.getCurrentCurriculum().getCurriculumId();
			curriculumName = parser.getCurrentCurriculum().getName();
			//universityName = parser.getCurrentUniversity().getName();

			studMode = parser.getCurrentCurriculum().getMode();
		}
	}
	private void initComponents() {

		studyProgressBar = (ProgressBar) findViewById(R.id.study_progress_bar);
		semesterButtons = new ArrayList<Button>();

		studyProgressPercentage = (TextView) findViewById(R.id.progress_text_view);
		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		curriculumNameTextField.setText(curriculumName);

		setCourseListViews(new ListView[GlobalProperties.SEM_COUNT]);
		setAdapters(new CourseListAdapter[GlobalProperties.SEM_COUNT]);

		semesterTextField = (TextView) findViewById(R.id.semester_line_description_text_view);
		getCourseListViews()[0] = (ListView) findViewById(R.id.courses_list_view_sem1);
		getCourseListViews()[1] = (ListView) findViewById(R.id.courses_list_view_sem2);
		getCourseListViews()[2] = (ListView) findViewById(R.id.courses_list_view_sem3);
		getCourseListViews()[3] = (ListView) findViewById(R.id.courses_list_view_sem4);
		getCourseListViews()[4] = (ListView) findViewById(R.id.courses_list_view_sem5);
		getCourseListViews()[5] = (ListView) findViewById(R.id.courses_list_view_sem6);
		getCourseListViews()[6] = (ListView) findViewById(R.id.courses_list_view_opt_courses);

		semesterButtons.add((Button) findViewById(R.id.semester_1_name_button));
		semesterButtons.add((Button) findViewById(R.id.semester_2_name_button));
		semesterButtons.add((Button) findViewById(R.id.semester_3_name_button));
		semesterButtons.add((Button) findViewById(R.id.semester_4_name_button));
		semesterButtons.add((Button) findViewById(R.id.semester_5_name_button));
		semesterButtons.add((Button) findViewById(R.id.semester_6_name_button));
		semesterButtons
				.add((Button) findViewById(R.id.semester_optional_courses));

		if (studMode == GlobalProperties.DIPL_STUD) {
			for (int i = 3; i < semesterButtons.size() - 1; i++)
				semesterButtons.get(i).setVisibility(View.INVISIBLE);
			semesterTextField.setText("Abschnitt");
		} else if (studMode == GlobalProperties.MAST_STUD) {
			for (int i = 4; i < semesterButtons.size() - 1; i++)
				semesterButtons.get(i).setVisibility(View.INVISIBLE);
		} else if (studMode == GlobalProperties.LA_STUD) {
			// TODO:+3 Sem Buttons
		}

		refreshProgress();

		String[][] courseNames = null;
		courseNames = new String[GlobalProperties.SEM_COUNT][];

		for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
			courseNames[i] = parser.getCourseNamesOfSemester(i + 1);

		for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
			getAdapters()[i] = new CourseListAdapter(courseNames[i], this);

		for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
			getCourseListViews()[i].setAdapter(getAdapters()[i]);

		setClickListneners();
		setBackgroudColors();

	}

	private void setBackgroudColors() {
		for (int j = 0; j < GlobalProperties.SEM_COUNT; j++)
			for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
				if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_DONE) {
					int position = getAdapters()[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						getAdapters()[j].setViewBackgroundColor(position,
								Color.GREEN);
				} else if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_IN_PROGRESS) {
					int position = getAdapters()[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						getAdapters()[j].setViewBackgroundColor(position,
								Color.YELLOW);
				} else if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_TO_DO) {

					int position = getAdapters()[j].getPositionByString(parser
							.getCurrentCourses().get(i).getCourseName());
					if (position != -1)
						getAdapters()[j].setViewBackgroundColor(position,
								Color.RED);
				}
			}
	}

	private void setClickListneners() {
		for (int i = 0; i < semesterButtons.size(); i++) {
			semesterButtons.get(i).setOnTouchListener(setupOnTouchListeners(i));
		}

		for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {
			getCourseListViews()[i]
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
		case android.R.id.home:
			Log.d("t2",studyStateChanged+"");
			onBackPressed();
			return true;
		case R.id.save_item:
			saveCourse();
			return true;
		case R.id.delete_item:
			DeleteMenuCallback dmc = new DeleteMenuCallback(this);
			startActionMode(dmc);
			return true;
		case R.id.add_item:
			Intent intent = new Intent(MainActivity.this,
					CreateOptionalCoursesActivity.class);
			startActivity(intent);
			finish();
			return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void saveCourse() {
		studyStateChanged = false;
		XMLSave saver = new XMLSave(parser.getCurrentCourses());
		saver.saveXML("my_curriculum.xml", "placeholder", curriculumName, curriculumId, studMode);
		Toast.makeText(getBaseContext(), R.string.save_text_succ,
				Toast.LENGTH_SHORT).show();
	}

	public OnTouchListener setupOnTouchListeners(final int semester) {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("t2",event.toString());
				if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE){
					for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {
						if (i == semester) {
							semesterButtons.get(i).setPressed(true);
							getCourseListViews()[i].setVisibility(View.VISIBLE);
						} else {
							semesterButtons.get(i).setPressed(false);

							getCourseListViews()[i]
									.setVisibility(View.INVISIBLE);
						}

						getCourseListViews()[i].refreshDrawableState();
					}
					return true;

				}
				return false;
			}
		};
	}

	//TODO: class for AlertDialog!
	public OnItemClickListener setupOnItemClickListener(final int semester) {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				studyStateChanged = true;

				final String courseName = getCourseListViews()[semester]
						.getItemAtPosition(position).toString();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);

				builder.setTitle(courseName);

				String courseInformation = "ECTS: ";
				float courseEcts = parser.getEctsByName(courseName);
				String ectsString = Float.valueOf(courseEcts).toString();
				courseInformation += ectsString + "\n";
				courseInformation += "Kursnummer: ";
				courseInformation += parser.getCourseNumberByName(courseName);
				courseInformation += "\n";
				courseInformation += "Steop: ";
				int courseSteop = parser.getCourseSteopByName(courseName);
				if (courseSteop == 1) {
					courseInformation += "Ja\n";
				} else
					courseInformation += "Nein\n";

				courseInformation += "Modus: ";
				courseInformation += parser.getCourseModeByName(courseName);
				courseInformation += "\n";

				builder.setMessage(courseInformation);
				builder.setPositiveButton(R.string.done,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								getAdapters()[semester].setViewBackgroundColor(
										position, Color.GREEN);
								setProgressOfCourseDone(courseName);

							}
						});

				builder.setNegativeButton(R.string.todo,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								getAdapters()[semester].setViewBackgroundColor(
										position, Color.RED);

								setProgressOfCourseTodo(courseName);

							}
						});

				builder.setNeutralButton(R.string.inprogress,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								getAdapters()[semester].setViewBackgroundColor(
										position, Color.YELLOW);

								setProgressOfCourseInProgress(courseName);
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		};
	}	
	

//    public void clickHandler(final View v)
//    {
//	    switch(v.getId())
//	    {
//	    case R.id.alert_diag_course_delete_button:
//	    	
//	    	XMLParser.getInstance(null).deleteCourse(courseSelected);
//	    	studyStateChanged = true;
//	    	adapters[semesterSelected].setCourseNames(courseNames, position);;
//	    	Toast.makeText(getApplicationContext(), "You clicked on delete!!", Toast.LENGTH_LONG).show();
//	    	break;
//	    case R.id.alert_diag_course_edit_button:
//	    	Toast.makeText(getApplicationContext(), "You clicked on edit!!",Toast.LENGTH_LONG).show();
//	    	break;
//	    	
//	    
//	    }
//    }
	

	public void refreshProgress() {
		ProgressCalculator calculator = new ProgressCalculator(parser);
		float currentEcts = calculator.calcuateCurrentECTS();
		int maxECTS = calculator.getMaxECTS(GlobalProperties.STATIC_ECTS_MODE);

		studyProgressBar.setMax(maxECTS);
		studyProgressBar.setProgress((int) currentEcts);
		studyProgressBar.refreshDrawableState();

		float progressInPercent = calculator.calculatePercentage();

		studyProgressPercentage.setText(currentEcts + "/" + maxECTS + " ECTS ("
				+ progressInPercent + "%)");
	}

	private void setProgressOfCourseDone(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i,
						GlobalProperties.STATUS_DONE);
		}

		refreshProgress();

	}

	public void setStudyStateChanged() {
		studyStateChanged = true;
	}

	private void setProgressOfCourseTodo(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i,
						GlobalProperties.STATUS_TO_DO);
		}

		refreshProgress();

	}

	private void setProgressOfCourseInProgress(String courseName) {
		for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
			if (parser.getCurrentCourses().get(i).getCourseName()
					.equals(courseName))
				parser.setStatusOfCurrentCourseTo(i,
						GlobalProperties.STATUS_IN_PROGRESS);
		}

		refreshProgress();

	}

	@Override
	public void onBackPressed() {
		if (studyStateChanged) {
			studyStateChanged = false;
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

	public static ListView[] getCourseListViews() {
		return courseListViews;
	}

	public static void setCourseListViews(ListView[] courseListViews) {
		MainActivity.courseListViews = courseListViews;
	}

	public static CourseListAdapter[] getAdapters() {
		return adapters;
	}

	public static void setAdapters(CourseListAdapter[] adapters) {
		MainActivity.adapters = adapters;
	}

}
