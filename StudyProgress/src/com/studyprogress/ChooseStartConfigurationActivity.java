package com.studyprogress;

import com.example.studyprogress.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChooseStartConfigurationActivity extends Activity {
	private Button newPlanButton;
	private Button openPlanButton;
	private Button aboutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_start_configuration);

		newPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_new);
		openPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_open);
		aboutButton = (Button) findViewById(R.id.choose_start_configuration_button_about);

		newPlanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseStartConfigurationActivity.this,
						ChooseExistingOrNewCurriculumActivity.class);
				startActivity(intent);

			}
		});
		openPlanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseStartConfigurationActivity.this,
						MainActivity.class);
				intent.putExtra("firstOpen", 0);
				startActivity(intent);

			}
		});
		
		aboutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseStartConfigurationActivity.this,
						AboutActivity.class);
				intent.putExtra("firstOpen", 0);
				startActivity(intent);

			}
		});
		
		
	}
}
