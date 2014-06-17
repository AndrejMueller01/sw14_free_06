package com.studyprogress;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.tools.XMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateNewCurriculumActivity extends Activity {
	private EditText currNameField;
	private Spinner currModeSpinner;
	private XMLParser parser;

	private static int studId = 99999;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_curriculum);
		ActionBarProperties.noTitleText(this);

		currNameField = (EditText) findViewById(R.id.create_new_curr_name_edit_text);
		currModeSpinner = (Spinner) findViewById(R.id.create_new_curr_mode_spinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_curriculum_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = new Intent(CreateNewCurriculumActivity.this,
				MainActivity.class);
		switch (item.getItemId()) {
		case R.id.create_curriculum_ok_item:
			
			parser = XMLParser.getInstance(null);
			String studName = currNameField.getText().toString();
			if(TextUtils.isEmpty(studName))
			{
				Toast.makeText(getApplicationContext(), R.string.new_curriculum_name_exception, Toast.LENGTH_LONG).show();
				return false;
			}

			int studMode = currModeSpinner.getSelectedItemPosition();
			studId++;
			parser.setCurrentCurriculum(studName, studMode, studId);
			intent.putExtra("firstOpen", 1);
			startActivity(intent);
			finish();
			return true;
			
		case R.id.create_curriculum_cancel_item:
			onBackPressed();
			finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
