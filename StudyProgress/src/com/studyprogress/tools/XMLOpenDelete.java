package com.studyprogress.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import com.studyprogress.MainActivity;
import com.studyprogress.R;
import com.studyprogress.properties.ActivityIntentExtras;
import com.studyprogress.properties.GlobalProperties;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Andrej on 20.07.2014.
 */
public class XMLOpenDelete {

    private String[] fileList;
    private File path;
    private Context context;
    private String chosenFile;

    public XMLOpenDelete(Context context) {
        this.context = context;
        path = new File(Environment.getExternalStorageDirectory().toString() + GlobalProperties.SAVE_FILE_DIR);
    }

    public void performOpen() {
        loadFileList();
        onOpenDialog();
    }

    public void performDelete() {
        loadFileList();
        onDeleteDialog();
    }

    private void loadFileList() {

        if (path.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File selectedFile = new File(dir, filename);
                    return filename.contains(GlobalProperties.XML_EXTENSION) || selectedFile.isDirectory();
                }
            };
            fileList = path.list(filter);
        } else {
            fileList = new String[0];
        }
        removeXMLExtension();

    }

    private void removeXMLExtension() {
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = fileList[i].replaceFirst("[.][^.]+$", "");
        }
    }

    protected Dialog onOpenDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.choose_xml_open_message);
        if (fileList == null) {
            dialog = builder.create();
            return dialog;
        }
        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                chosenFile = fileList[which] + GlobalProperties.XML_EXTENSION;
                Intent intent = new Intent(
                        context,
                        MainActivity.class);
                File file = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
                        chosenFile
                );
                if (file.exists()) {
                    intent.putExtra(ActivityIntentExtras.FIRST_TIME_OPENED, GlobalProperties.FROM_OPEN_CURR);
                    intent.putExtra(ActivityIntentExtras.XML_FILE_NAME, chosenFile);

                    context.startActivity(intent);
                } else {
                    Toast.makeText(context.getApplicationContext(),
                            R.string.file_not_found, Toast.LENGTH_LONG).show();
                }

            }
        });
        dialog = builder.show();
        return dialog;
    }

    protected Dialog onDeleteDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.choose_xml_open_message);
        if (fileList == null) {
            dialog = builder.create();
            return dialog;
        }
        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                chosenFile = fileList[which] + GlobalProperties.XML_EXTENSION;

                File file = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
                        chosenFile
                );
                if (file.exists()) {
                    performAreYouShureDialog(file);
                } else {
                    Toast.makeText(context.getApplicationContext(),
                            R.string.file_not_found, Toast.LENGTH_LONG).show();
                }

            }
        });
        dialog = builder.show();
        return dialog;
    }

    public void performAreYouShureDialog(final File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = context.getString(R.string.are_you_sure_to_delete_message, chosenFile);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int id) {
                file.delete();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
