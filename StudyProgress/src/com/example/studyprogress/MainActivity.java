package com.example.studyprogress;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView curriculumNameTextField;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
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
		return true;
	}

}
