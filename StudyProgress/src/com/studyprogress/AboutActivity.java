package com.studyprogress;

import com.studyprogress.R;
import com.studyprogress.properties.ActionBarProperties;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		ActionBarProperties.noTitleText(this);

	}
}
