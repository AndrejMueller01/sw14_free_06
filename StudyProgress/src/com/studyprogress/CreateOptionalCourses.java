package com.studyprogress;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateOptionalCourses extends Activity{
	private XMLParser parser;
	private EditText courseNameET;
	private EditText ectsET;
	private EditText semET;

	private Spinner modeSP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_optional_course);
		courseNameET = (EditText) findViewById(R.id.create_course_course_name_edit_text);
		ectsET = (EditText) findViewById(R.id.create_course_ects_edit_text);
		semET = (EditText) findViewById(R.id.create_course_sem_edit_text);
		modeSP = (Spinner) findViewById(R.id.create_course_mode_spinner);
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
		Intent intent = new Intent(CreateOptionalCourses.this,
				MainActivity.class);
		switch (item.getItemId()) {
		case R.id.create_courses_ok_item:
			Course newCourse = new Course();
			newCourse.setCourseName(courseNameET.getText().toString());
			newCourse.setEcts(Float.parseFloat(ectsET.getText().toString()));
			newCourse.setSemester(7);
			newCourse.setCurricula(parser.getCurrentCurriculum().getCurriculumId());
			newCourse.setSemester(Integer.parseInt(semET.getText().toString()));

			parser.addCourseToCurrentCourses(newCourse);
			
			intent.putExtra("firstOpen", false);
			intent.putExtra("fromCreateNew", true);
			startActivity(intent);
			return true;
			
		case R.id.create_courses_cancel_item:

			intent.putExtra("firstOpen", false);
			startActivity(intent);
			
			return true;
			
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
