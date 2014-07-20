package com.studyprogress.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import com.example.studyprogress.R;
import com.studyprogress.MainActivity;
import com.studyprogress.properties.GlobalProperties;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Andrej on 20.07.2014.
 */
public class XMLOpen {

    private String[] fileList;
    private File mPath;
    private Context context;
    private String chosenFile;
    private static final int DIALOG_LOAD_FILE = 1000;

    public XMLOpen(Context context) {
        this.context = context;
        mPath = new File(Environment.getExternalStorageDirectory().toString() + "/studyprogress_save");
    }

    public void performOpen() {
        loadFileList();
        onCreateDialog();
    }
    public int getFileCount(){
        loadFileList();
        return fileList.length;
    }
    private void loadFileList() {

        if (mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File selectedFile = new File(dir, filename);
                    return filename.contains(GlobalProperties.XML_EXTENSION) || selectedFile.isDirectory();
                }
            };
            fileList = mPath.list(filter);
        } else {
            fileList = new String[0];
        }
    }

    protected Dialog onCreateDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle("Choose your file");
        if (fileList == null) {
            dialog = builder.create();
            return dialog;
        }
        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                chosenFile = fileList[which];
                Intent intent = new Intent(
                        context,
                        MainActivity.class);
                File file = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
                        chosenFile
                );
                if (file.exists()) {
                    intent.putExtra("firstOpen", 0);
                    intent.putExtra("xmlFileName", chosenFile);

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
}
