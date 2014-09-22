package com.studyprogress;

import com.studyprogress.R;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateOptionalCoursesActivity extends Activity {
	private static final int SEM_PLUS = 7;
	private XMLParser parser;
	private EditText courseNameET;
	private EditText ectsET;
	private EditText cidET;
	private Spinner semSP;
	private Spinner modeSP;
    private CheckBox steopCB;
    private Intent intent;

    private int studMode;
    private int studSem = GlobalProperties.SEM_COUNT-1;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_optional_course);
		ActionBarProperties.noTitleText(this);
        intent  = new Intent(CreateOptionalCoursesActivity.this,
                MainActivity.class);
		courseNameET = (EditText) findViewById(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) findViewById(R.id.create_course_ects_edit_text);
		cidET = (EditText) findViewById(R.id.create_course_cid_edit_text);
		semSP = (Spinner) findViewById(R.id.create_course_sem_spinner);
		modeSP = (Spinner) findViewById(R.id.create_course_mode_spinner);
        steopCB = (CheckBox) findViewById(R.id.create_course_is_steop_cb);
		parser = XMLParser.getInstance(null);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(ActivityIntentExtras.STUD_MODE))
            studMode = extras.getInt(ActivityIntentExtras.STUD_MODE);
        if (extras.containsKey(ActivityIntentExtras.STUD_SEM))
            studSem = extras.getInt(ActivityIntentExtras.STUD_SEM);

        ArrayList<String> semesterDescription = new ArrayList<String>();
        fillArrayListWithSemesters(semesterDescription);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, semesterDescription);
        semSP.setAdapter(adapter);
        if( studSem< GlobalProperties.SEM_COUNT-1)
        semSP.setSelection(studSem);
        else
         semSP.setSelection(semSP.getCount()-1);

	}

    private void fillArrayListWithSemesters(ArrayList<String> semesterDescription) {
        int counter = GlobalProperties.SEM_COUNT;
        if(studMode == GlobalProperties.BACH_STUD)
            counter -= 3;
        if(studMode == GlobalProperties.MAST_STUD)
            counter -= 5;
        if(studMode == GlobalProperties.DIPL_STUD)
            counter -= 6;
        if(studMode == GlobalProperties.PHD_STUD)
            counter -= 5;

        for(int i = 0; i < counter; i++){
            if(i == counter -1) {
                semesterDescription.add("Freifächer");
                break;
            }
            int value = i+1;
            semesterDescription.add(String.valueOf(value));
        }
    }


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_courses_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

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
            performCancelAction();
            return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}
    private void performCancelAction(){

        intent.putExtra(ActivityIntentExtras.FIRST_TIME_OPENED, GlobalProperties.FROM_ADDING_COURSES);
        intent.putExtra(ActivityIntentExtras.SOMETHING_CHANGED, false);

        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        performCancelAction();
    }
}
