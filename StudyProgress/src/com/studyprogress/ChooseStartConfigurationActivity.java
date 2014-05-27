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
	private Button newPlan;
	private Button openPlan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_start_configuration);

		newPlan = (Button) findViewById(R.id.choose_start_configuration_button_new);
		openPlan = (Button) findViewById(R.id.choose_start_configuration_button_open);

		newPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseStartConfigurationActivity.this,
						ChooseExistingOrNewCurriculum.class);
				startActivity(intent);

			}
		});
		openPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseStartConfigurationActivity.this,
						MainActivity.class);
				intent.putExtra("firstOpen", 0);
				startActivity(intent);

			}
		});
	}
}
