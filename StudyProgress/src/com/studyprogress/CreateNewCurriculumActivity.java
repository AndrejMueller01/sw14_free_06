package com.studyprogress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studyprogress.R;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.tools.XMLParser;

public class CreateNewCurriculumActivity extends Activity {

    private EditText currNameField;
    private Spinner currModeSpinner;
    private EditText univNameField;

    private XMLParser parser;

    // TODO: Id creator
    private static int studId = 99999;
    private static int univId = 99999;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_curriculum);
        ActionBarProperties.noTitleText(this);

        currNameField = (EditText) findViewById(R.id.create_new_curr_name_edit_text);
        currModeSpinner = (Spinner) findViewById(R.id.create_new_curr_mode_spinner);
        univNameField = (EditText) findViewById(R.id.create_new_curr_univ_name_edit_text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_curriculum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent = new Intent(CreateNewCurriculumActivity.this,
                MainActivity.class);
        switch (item.getItemId()) {

            case R.id.create_curriculum_ok_item:
                parser = XMLParser.getInstance(null);
                String studName = currNameField.getText().toString();
                String univName = univNameField.getText().toString();

                if(!checkEditTextFields(univName,studName))
                    return false;

                int studMode = currModeSpinner.getSelectedItemPosition();
                studId++;
                univId++;

                parser.setCurrentUniversity(univName, univId);
                parser.setCurrentCurriculum(univName, studName, studMode, studId);

                intent.putExtra("firstOpen", 1);
                startActivity(intent);
                finish();
                return true;

            case R.id.create_curriculum_cancel_item:
                onBackPressed();
                finish();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    private boolean checkEditTextFields(String studName, String univName){
        if (TextUtils.isEmpty(univName)) {
            Toast.makeText(getApplicationContext(), R.string.new_curriculum_uname_exception, Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(studName)) {
            Toast.makeText(getApplicationContext(), R.string.new_curriculum_cname_exception, Toast.LENGTH_LONG).show();
            return false;
        }
    return true;
    }
}
