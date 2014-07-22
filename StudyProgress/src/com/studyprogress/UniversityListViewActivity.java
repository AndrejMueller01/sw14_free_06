package com.studyprogress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.studyprogress.R;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.WebXMLLoader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class UniversityListViewActivity extends ListViewActivity {


    private ListView universityListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        xmlFileName = GlobalProperties.UNIVERSITY_XML_PREFIX;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_view);
        WebXMLLoader webXMLLoader = new WebXMLLoader(this);
        webXMLLoader.execute(xmlFileName);
    }

    @Override
    public void initComponents() {
        universityListView = (ListView) findViewById(R.id.university_list_view);
        searchTextField = (EditText) findViewById(R.id.university_list_view_search_input_field);
        parser.parseUniversities();

        try {
            namesInList = parser.getUniversityNames();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.university_list_item,
                R.id.university_text_view, namesInList);

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


}
