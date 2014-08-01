package com.studyprogress;

import java.io.File;
import java.util.List;

import com.example.studyprogress.R;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLOpen;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
	private Button siteButton;
    private Button loadExternalFilesButton;
    private XMLOpen xmlOpen = new XMLOpen(this);



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_start_configuration);
       	newPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_new);
		openPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_open);
		aboutButton = (Button) findViewById(R.id.choose_start_configuration_button_about);
		bugButton = (Button) findViewById(R.id.choose_start_configuration_button_bug);
		siteButton = (Button) findViewById(R.id.choose_start_configuration_button_site);
		newPlanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ChooseStartConfigurationActivity.this,
						ChooseExistingOrNewCurriculumActivity.class);
				startActivity(intent);

			}
		});

        openPlanButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                xmlOpen.performOpen();
			}
		});

		aboutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ChooseStartConfigurationActivity.this,
						AboutActivity.class);
				intent.putExtra("firstOpen", 0);
				startActivity(intent);

			}

		});
		bugButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String url = "https://www.facebook.com/groups/253869021486505/";
				Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url));
				facebookAppIntent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

				startActivity(facebookAppIntent);

			}
		});
		siteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String url = "http://www.studyprogress.at";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url));
				intent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

				startActivity(intent);

			}
		});



    }
}
