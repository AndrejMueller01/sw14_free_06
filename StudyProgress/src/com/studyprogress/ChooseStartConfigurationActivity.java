package com.studyprogress;

import java.io.File;

import com.example.studyprogress.R;
import com.studyprogress.properties.GlobalProperties;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseStartConfigurationActivity extends Activity {
	private Button newPlanButton;
	private Button openPlanButton;
	private Button aboutButton;
	private Button bugButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_start_configuration);

		newPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_new);
		openPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_open);
		aboutButton = (Button) findViewById(R.id.choose_start_configuration_button_about);
		bugButton = (Button) findViewById(R.id.choose_start_configuration_button_bug);

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
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
						GlobalProperties.SAVE_FILE_NAME);
				if(file.exists())   {  
					intent.putExtra("firstOpen", 0);
				startActivity(intent);	
				}
				else{
					Toast.makeText(getApplicationContext(), R.string.file_not_found, Toast.LENGTH_LONG).show();
				}

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
		bugButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String url = "fb://page/1503204023244122";
						Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						facebookAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						startActivity(facebookAppIntent);

			}
		});
		
	}
}
