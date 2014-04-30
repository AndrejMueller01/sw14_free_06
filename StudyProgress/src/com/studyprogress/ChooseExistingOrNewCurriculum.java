package com.studyprogress;

import com.example.studyprogress.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseExistingOrNewCurriculum extends Activity{
	
	private Button newCurriculum;
	private Button openTemplate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_new_existing_curriculum);

		newCurriculum = (Button) findViewById(R.id.choose_existing_new_curriculum_button_new);
		openTemplate = (Button) findViewById(R.id.choose_existing_new_curriculum_button_open);

		newCurriculum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseExistingOrNewCurriculum.this,
						CreateNewCurriculum.class);
				startActivity(intent);

			}
		});
		
		openTemplate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseExistingOrNewCurriculum.this,
						CurriculumListViewActivity.class);
				startActivity(intent);

			}
		});
		
	}
}
