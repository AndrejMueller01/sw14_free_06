package com.studyprogress;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.example.studyprogress.R;
import com.studyprogress.tools.XMLParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UniversityListViewActivity extends Activity {

	private static ArrayAdapter<String> adapter;
	private ListView universityListView;
	private EditText searchTextField;
	private ArrayList<String> universityNames = null;
	private ArrayList<String> universityNamesSearchFaults = null;
	private XMLParser parser ;

	private int tempCsLength = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_university_view);
		initComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		universityNamesSearchFaults = new ArrayList<String>();
		return true;
	}

	public void initComponents() {

		universityListView = (ListView) findViewById(R.id.university_list_view);
		searchTextField = (EditText) findViewById(R.id.university_list_view_search_input_field);

		InputStream is = this.getResources().openRawResource(R.raw.universities);
		parser= XMLParser.getInstance(is);
		parser.parseUniversities();

		try {
			universityNames = parser.getUniversityNames(is);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new ArrayAdapter<String>(this, R.layout.university_list_item,
				R.id.university_text_view, universityNames);

		Log.d("t6", "Name: "+universityNames.get(0)+adapter.toString());
		universityListView.setAdapter(adapter);
		updateListViewOnSearching();
		universityListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(UniversityListViewActivity.this,
						CurriculumListViewActivity.class);
				String universityName = (String) universityListView.getAdapter().getItem(position);
				int universityId = parser.getUniversityIdWithName(universityName);
				parser.setCurrentUniversity(universityName, universityId);
				startActivity(intent);
				finish();

			}
		});

	}

	public void updateListViewOnSearching() {

		searchTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() < tempCsLength) {
					for (int i = 0; i < universityNamesSearchFaults.size(); i++)
						universityNamesSearchFaults.add(universityNamesSearchFaults.get(i));

					adapter.notifyDataSetChanged();
					universityNamesSearchFaults.clear();

				}
				if (cs.length() >= 1) {
					
					filterOnSearchTextChanged(cs);
				}
				tempCsLength = cs.length();
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

	public void filterOnSearchTextChanged(CharSequence cs) {
		for (int i = 0; i < universityNames.size(); i++) {
			if (!universityNames.get(i).startsWith(cs.toString())) {
				universityNamesSearchFaults.add(universityNames.get(i));
				universityNames.remove(i);
				i--;
				adapter.notifyDataSetChanged();
			}

		}

	}
}
