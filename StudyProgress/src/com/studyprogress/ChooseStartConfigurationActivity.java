package com.studyprogress;

import com.example.studyprogress.R;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLOpenDelete;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseStartConfigurationActivity extends Activity {
	private Button newPlanButton;
	private Button openPlanButton;
    private Button deletePlanButton;

    private Button aboutButton;
	private Button bugButton;
	private Button siteButton;
    private XMLOpenDelete xmlOpenDelete = new XMLOpenDelete(this);



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_start_configuration);
       	newPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_new);
		openPlanButton = (Button) findViewById(R.id.choose_start_configuration_button_open);
        deletePlanButton = (Button) findViewById(R.id.choose_start_configuration_button_delete);
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
                xmlOpenDelete.performOpen();
			}
		});
        deletePlanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                xmlOpenDelete.performDelete();
            }
        });

		aboutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ChooseStartConfigurationActivity.this,
						AboutActivity.class);
				startActivity(intent);

			}

		});
		bugButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String url = GlobalProperties.FACEBOOK_GROUP_URL;
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
				final String url = GlobalProperties.WEBSITE_URL;
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url));
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

				startActivity(intent);

			}
		});



    }
}
