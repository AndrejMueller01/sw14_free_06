package com.studyprogress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLOpenDelete;

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
       // bugButton = (Button) findViewById(R.id.choose_start_configuration_button_bug);
        siteButton = (Button) findViewById(R.id.choose_start_configuration_button_site);
        newPlanButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(ChooseStartConfigurationActivity.this);
                dialog.setContentView(R.layout.dialog_new_or_existing);
                dialog.setTitle(R.string.dialog_new_or_existing_title);

                Button dialogButtonNew = (Button) dialog.findViewById(R.id.choose_existing_new_curriculum_button_new);
                dialogButtonNew.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseStartConfigurationActivity.this,
                                CreateNewCurriculumActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                Button dialogButtonExisting = (Button) dialog.findViewById(R.id.choose_existing_new_curriculum_button_open);
                dialogButtonExisting.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(
                                ChooseStartConfigurationActivity.this).create();
                        alertDialog.setTitle(R.string.dialog_alert_new_or_existing_title);

                        alertDialog.setMessage(getResources().getString(R.string.dialog_alert_new_or_existing_message));

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ChooseStartConfigurationActivity.this,
                                        UniversityListViewActivity.class);
                                startActivity(intent);
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.menu_item_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();

                        dialog.dismiss();

                    }
                });
                dialog.show();

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
