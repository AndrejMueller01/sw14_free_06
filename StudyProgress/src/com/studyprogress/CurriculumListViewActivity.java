package com.studyprogress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.studyprogress.properties.ActivityIntentExtras;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.WebXMLLoader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class CurriculumListViewActivity extends ListViewActivity {

    private ListView curriculumListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xmlFileName = GlobalProperties.CURRICULA_XML_PREFIX + parser.getCurrentUniversity().getId();
        setContentView(R.layout.activity_curriculum_view);
        WebXMLLoader webXMLLoader = new WebXMLLoader(this);
        webXMLLoader.execute(xmlFileName);

    }

    @Override
    public void initComponents() {


        curriculumListView = (ListView) findViewById(R.id.curriculum_list_view);
        searchTextField = (EditText) findViewById(R.id.crurriculum_list_view_search_input_field);

        parser.parseCurricula();

        try {
            namesInList = parser.getCurriculaNames();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        adapter = new ArrayAdapter<String>(this, R.layout.curriculum_list_item,
                R.id.curriculum_text_view, namesInList);

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
                parser.setCurrentCurriculum(parser.getCurrentUniversity().getName(), curriculumName, studMode, curriculumId);

                intent.putExtra(ActivityIntentExtras.FIRST_TIME_OPENED, GlobalProperties.FIRST_TIME_OPENED);
                startActivity(intent);
                finish();

            }
        });

    }


}
