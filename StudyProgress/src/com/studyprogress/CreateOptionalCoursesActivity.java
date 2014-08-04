package com.studyprogress;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.properties.ActivityIntentExtras;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateOptionalCoursesActivity extends Activity {
	private static final int SEM_PLUS = 7;
	private XMLParser parser;
	private EditText courseNameET;
	private EditText ectsET;
	private EditText cidET;
	private Spinner semSP;
	private Spinner modeSP;
    private CheckBox steopCB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_optional_course);
		ActionBarProperties.noTitleText(this);

		courseNameET = (EditText) findViewById(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) findViewById(R.id.create_course_ects_edit_text);
		cidET = (EditText) findViewById(R.id.create_course_cid_edit_text);
		semSP = (Spinner) findViewById(R.id.create_course_sem_spinner);
		modeSP = (Spinner) findViewById(R.id.create_course_mode_spinner);
        steopCB = (CheckBox) findViewById(R.id.create_course_is_steop_cb);
		parser = XMLParser.getInstance(null);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_courses_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = new Intent(CreateOptionalCoursesActivity.this,
				MainActivity.class);
		switch (item.getItemId()) {
		case R.id.create_courses_ok_item:
			Course newCourse = new Course();
			
			String courseName = courseNameET.getText().toString();
			String ects = ectsET.getText().toString();
			String semesterNo = semSP.getSelectedItem().toString();
			String cid = cidET.getText().toString();
			String courseMode = modeSP.getSelectedItem().toString();
			if(TextUtils.isEmpty(cid))
				cid = getResources().getString(R.string.not_avaiable);
            if(steopCB.isChecked())
                newCourse.setSteop(1);
			
			
			newCourse.setCourseName(courseName);
			if(TextUtils.isEmpty(newCourse.getCourseName()))
			{
				Toast.makeText(getApplicationContext(), R.string.new_course_name_exception, Toast.LENGTH_LONG).show();
				return false;
			}
			
			try {
			newCourse.setEcts(Float.parseFloat(ects));
			} catch (NumberFormatException ex) {
				Toast.makeText(getApplicationContext(), R.string.new_course_ects_exception, Toast.LENGTH_LONG).show();
				return false;
			}

			try {
			newCourse.setCourseNumber(cid);;
			} catch (NumberFormatException ex) {
				Toast.makeText(getApplicationContext(), R.string.new_course_ects_exception, Toast.LENGTH_LONG).show();
				return false;
			}

			
			if(newCourse.getEcts() == 0)
			{
				Toast.makeText(getApplicationContext(), R.string.new_course_ects_exception, Toast.LENGTH_LONG).show();
				return false;
			}
			
			// TODO: 
			try {
				newCourse.setSemester(Integer.parseInt(semesterNo));
			} catch (NumberFormatException ex) {
				newCourse.setSemester(SEM_PLUS);
			}
			
			try {
				newCourse.setMode(courseMode);
			} catch (NumberFormatException ex) {
				newCourse.setMode(courseMode);
			}


			parser.addCourseToCurrentCourses(newCourse);

			intent.putExtra(ActivityIntentExtras.FIRST_TIME_OPENED, GlobalProperties.FROM_ADDING_COURSES);
			intent.putExtra(ActivityIntentExtras.SOMETHING_CHANGED, true);


            startActivity(intent);
			finish();
			return true;

		case R.id.create_courses_cancel_item:

			intent.putExtra(ActivityIntentExtras.FIRST_TIME_OPENED, GlobalProperties.FROM_ADDING_COURSES);
			intent.putExtra(ActivityIntentExtras.SOMETHING_CHANGED, false);

            startActivity(intent);
			finish();
			return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}

}
