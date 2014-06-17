package com.studyprogress;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.example.studyprogress.R;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.tools.XMLParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
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
import android.widget.Toast;

public class CurriculumListViewActivity extends Activity {

	private static ArrayAdapter<String> adapter;
	private ListView curriculumListView;
	private EditText searchTextField;
	private ArrayList<String> curriculumNames = null;
	private ArrayList<String> curriculumNamesSearchFaults = null;
	private XMLParser parser ;

	private int tempCsLength = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curriculum_view);
		ActionBarProperties.noTitleText(this);

		initComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		curriculumNamesSearchFaults = new ArrayList<String>();
		return true;
	}

	public void initComponents() {

		curriculumListView = (ListView) findViewById(R.id.curriculum_list_view);
		searchTextField = (EditText) findViewById(R.id.crurriculum_list_view_search_input_field);
	
		InputStream is = null;
		parser = XMLParser.getInstance(is);

		try {
			is = getResources().openRawResource(
					getResources().getIdentifier("curricula" + parser.getCurrentUniversity().getId(), "raw",
							getPackageName()));
			parser.setInputStream(is);
			parser.parseCurricula();

		} catch (NotFoundException ex) {

		}
		parser= XMLParser.getInstance(is);

		try {
			curriculumNames = parser.getCurriculaNames(is);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		adapter = new ArrayAdapter<String>(this, R.layout.curriculum_list_item,
				R.id.curriculum_text_view, curriculumNames);

		curriculumListView.setAdapter(adapter);
		updateListViewOnSearching();
		curriculumListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(CurriculumListViewActivity.this,
						MainActivity.class);
				String curriculumName = (String) curriculumListView.getAdapter().getItem(position);
				int curriculumId = parser.getCurriculumIdWithName(curriculumName);
				int studMode = parser.getCurriculumMode(curriculumName);
				parser.setCurrentCurriculum(curriculumName, studMode, curriculumId);

				intent.putExtra("firstOpen", 1);
				startActivity(intent);
				finish();

			}
		});

	}

	//TODO: ListView Activity super class
	public void updateListViewOnSearching() {

		searchTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() < tempCsLength) {
					for (int i = 0; i < curriculumNamesSearchFaults.size(); i++)
						curriculumNames.add(curriculumNamesSearchFaults.get(i));

					adapter.notifyDataSetChanged();
					curriculumNamesSearchFaults.clear();

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
		for (int i = 0; i < curriculumNames.size(); i++) {
			if (!curriculumNames.get(i).startsWith(cs.toString())) {
				curriculumNamesSearchFaults.add(curriculumNames.get(i));
				curriculumNames.remove(i);
				i--;
				adapter.notifyDataSetChanged();
			}

		}

	}
}
