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
import com.studyprogress.objects.Course;

import android.os.Bundle;
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
	
	private static Integer STATUS_DONE = 10;
	private static Integer STATUS_IN_PROGRESS = 11;
	private static Integer STATUS_TO_DO = 12;
	
	private Map<String, Integer> progressMap = new HashMap<String,Integer>();



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
		
		for(int i = 0; i< courseNames.length; i++)
		{
			progressMap.put(courseNames[i],STATUS_TO_DO);			
		}

		adapter = new ArrayAdapter<String>(this, R.layout.courses_list_item,
				R.id.courses_text_view, courseNames);

		courseListView.setAdapter(adapter);
		courseListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//final CharSequence[] process = {"To Do", "In Progress", "Done"};
				
				final View v = courseListView.getChildAt(position);
			    final String courseName = courseListView.getItemAtPosition(position).toString();
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Select your course process!");
				/*processDialog.setItems(process, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	
				    	selectedProcess(item);
				    }
				});*/
				
				builder.setPositiveButton("Done",  new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						v.setBackgroundColor(Color.GREEN);
						setProgressOfCourse(courseName, STATUS_DONE);
						
						}
					});
				
				builder.setNegativeButton("To Do",  new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						v.setBackgroundColor(Color.RED);
						setProgressOfCourse(courseName, STATUS_TO_DO);

						}
					});		

				builder.setNeutralButton("In Progress",  new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						v.setBackgroundColor(Color.YELLOW);
						setProgressOfCourse(courseName, STATUS_IN_PROGRESS);
						}
					});	
				
				AlertDialog alert = builder.create();
				alert.show();
				
				
				//courseListView.getChildAt(position).setBackgroundColor(Color.GREEN);
			}
		});

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	private void setProgressOfCourse(String courseName, Integer Progress)
	{
		progressMap.put(courseName, Progress);
	}

}
