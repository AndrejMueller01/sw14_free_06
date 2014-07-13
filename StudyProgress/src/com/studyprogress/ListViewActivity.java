package com.studyprogress;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.tools.XMLParser;

import java.util.ArrayList;

/**
 * Created by Andrej on 13.07.2014.
 */
public class ListViewActivity extends StudyProgressActivity {
    protected ArrayList<String> namesInList = null;
    protected static ArrayAdapter<String> adapter;
    protected EditText searchTextField;
    protected ArrayList<String> namesSearchFaults = null;
    protected XMLParser parser;
    private int tempCsLength = 0;
    protected String xmlFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarProperties.noTitleText(this);
        parser = XMLParser.getInstance(null);
        namesSearchFaults = new ArrayList<String>();

    }



    protected void updateListViewOnSearching() {

        searchTextField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() < tempCsLength) {
                    for (int i = 0; i < namesSearchFaults.size(); i++)
                        namesInList.add(namesSearchFaults.get(i));

                    adapter.notifyDataSetChanged();
                    namesSearchFaults.clear();

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
        for (int i = 0; i < namesInList.size(); i++) {
            if (!namesInList.get(i).startsWith(cs.toString())) {
                namesSearchFaults.add(namesInList.get(i));
                namesInList.remove(i);
                i--;
                adapter.notifyDataSetChanged();
            }

        }

    }

}
