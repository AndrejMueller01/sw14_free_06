package com.example.studyprogress;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import com.example.tools.XMLParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
		courseListView = (ListView) findViewById(R.id.coursesListView);
		
		String curriculumName;
		
		if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	curriculumName= null;
		    } else {
		    	// TODO: hardcoded 
		    	curriculumName= extras.getString("Id");
		    	curriculumNameTextField.setText(curriculumName);
		    }
		} else {
			curriculumName= (String) savedInstanceState.getSerializable("Id");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		InputStream is = this.getResources().openRawResource(R.raw.courses);
		
		XMLParser parser = new XMLParser(is);

		String[] courseNames = null;

		try {
			courseNames = parser.getCoursesNames(is);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adapter = new ArrayAdapter<String>(this, R.layout.courses_list_item,
				R.id.coursesListView, courseNames);

		courseListView.setAdapter(adapter);
		//updateListViewOnSearching();
		courseListView.setOnItemClickListener(new OnItemClickListener() {

             public void onItemClick(AdapterView<?> parent, View view,
                     int position, long id) {

                 
                     Intent intent = new Intent(MainActivity.this,
                             MainActivity.class);
                     intent.putExtra("Id", ((TextView)(view.findViewById(R.id.courses_text_view))).getText());
                     startActivity(intent);
                   
             }});
		
		

		
		return true;
	}

}
