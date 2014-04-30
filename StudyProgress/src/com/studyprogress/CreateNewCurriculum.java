package com.studyprogress;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CreateNewCurriculum extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_curriculum);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_curriculum_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = new Intent(CreateNewCurriculum.this,
				ChooseExistingOrNewCurriculum.class);
		switch (item.getItemId()) {
		case R.id.create_curriculum_ok_item:

		case R.id.create_curriculum_cancel_item:
			startActivity(intent);
			return true;

		}
		return super.onMenuItemSelected(featureId, item);
	}

	
	


}
