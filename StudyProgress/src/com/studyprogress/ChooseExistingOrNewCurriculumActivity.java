package com.studyprogress;

import com.studyprogress.R;
import com.studyprogress.properties.ActionBarProperties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseExistingOrNewCurriculumActivity extends Activity{
	
	private Button newCurriculumButton;
	private Button openTemplateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_new_existing_curriculum);
		ActionBarProperties.noTitleText(this);

		newCurriculumButton = (Button) findViewById(R.id.choose_existing_new_curriculum_button_new);
		openTemplateButton = (Button) findViewById(R.id.choose_existing_new_curriculum_button_open);

		newCurriculumButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseExistingOrNewCurriculumActivity.this,
						CreateNewCurriculumActivity.class);
				startActivity(intent);
				finish();

			}
		});
		
		openTemplateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseExistingOrNewCurriculumActivity.this,
						UniversityListViewActivity.class);
				startActivity(intent);
				finish();

			}
		});
		
	}
}
