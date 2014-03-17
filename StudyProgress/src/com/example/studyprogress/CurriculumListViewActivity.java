package com.example.studyprogress;
import com.example.tools.FileManager;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CurriculumListViewActivity extends Activity {

	private static ArrayAdapter<String> adapter;
	private ListView curriculumListView;
	private EditText searchTextField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curriculum_view);
		initComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public void initComponents() {

		curriculumListView = (ListView) findViewById(R.id.curriculum_list_view);
		searchTextField = (EditText) findViewById(R.id.search_input);

		adapter = new ArrayAdapter<String>(this, R.layout.curriculum_list_item,
				R.id.curriculum_name, FileManager.getCurriculaNames(
						R.raw.curricula, this));
		curriculumListView.setAdapter(adapter);
		updateListViewOnSearching();

	}

	public void updateListViewOnSearching() {
		searchTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				CurriculumListViewActivity.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}

		});
	}
}
