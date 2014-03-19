package com.example.studyprogress;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import com.example.tools.XMLParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
		// FileManager.getCurriculaNames(R.raw.curricula,this)


		InputStream is = this.getResources().openRawResource(R.raw.curricula);
		
		XMLParser parser = new XMLParser(is);

		String[] curriculumNames = null;
		try {
			curriculumNames = parser.getCurriculaNames(is);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
                     intent.putExtra("Id", ((TextView)(view.findViewById(R.id.curriculum_text_view))).getText());
                     startActivity(intent);
                   
             }});

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
