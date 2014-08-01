package com.studyprogress.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studyprogress.R;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.GlobalProperties;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

// TODO: refactor strings
public class XMLSave {

    private String rootDir = Environment.getExternalStorageDirectory().toString();
    private File appDir = new File(rootDir + "/studyprogress_save");
    private ArrayList<Course> courseList;
    private Context context;
    private String fileName;
    private String universityName;
    private String curriculumName;
    private int curriculumId;
    private int studMode;
    private boolean saveAndClose;


    public XMLSave(ArrayList<Course> currentCourses, Context context) {
        this.context = context;
        appDir.mkdirs();
        courseList = currentCourses;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void saveXML(final boolean saveAndClose, final boolean withDialog, String universityNameSave, String curriculumNameSave, int curriculumIdSave, int studModeSave) {
        this.saveAndClose = saveAndClose;
        this.universityName = universityNameSave;
        this.curriculumName = curriculumNameSave;
        this.curriculumId = curriculumIdSave;
        this.studMode = studModeSave;
        if (withDialog) {
            final EditText fileNameField = new EditText(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(R.string.save_xml_message)
                    .setView(fileNameField)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    fileName = fileNameField.getText().toString() + GlobalProperties.XML_EXTENSION;
                                    if (fileName.equals(GlobalProperties.XML_EXTENSION)) {
                                        Toast.makeText(context, R.string.new_course_name_exception, Toast.LENGTH_LONG).show();
                                        saveXML(saveAndClose, withDialog, universityName, curriculumName, curriculumId, studMode);
                                        return;
                                    }
                                    buildSaveFile();
                                }
                            }
                    )
                    .setNegativeButton(R.string.menu_item_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    return;
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            buildSaveFile();
            if (saveAndClose) {
                ((Activity) context).finish();
            }
        }


    }

    private void saveProcedure() {
        try {
            final File file = new File(appDir, fileName);
            FileOutputStream out = new FileOutputStream(file);

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(out, "UTF-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output",
                    true);

            serializer.startTag(null, "courses");
            serializer.attribute(null, "uname", universityName);
            serializer.attribute(null, "cname", curriculumName);
            serializer.attribute(null, "cid", "" + curriculumId);
            serializer.attribute(null, "cmode", "" + studMode);

            for (int j = 0; j < courseList.size(); j++) {
                //TODO: more informations
                serializer.startTag(null, "course");
                serializer.startTag(null, "name");
                serializer.text("" + courseList.get(j).getCourseName());
                serializer.endTag(null, "name");
                serializer.startTag(null, "id");
                serializer.text("" + courseList.get(j).getCurriculaNumber());
                serializer.endTag(null, "id");
                serializer.startTag(null, "ects");
                serializer.text("" + courseList.get(j).getEcts());
                serializer.endTag(null, "ects");
                serializer.startTag(null, "semester");
                serializer.text("" + courseList.get(j).getSemester());
                serializer.endTag(null, "semester");
                serializer.startTag(null, "bachelor");
                serializer.endTag(null, "bachelor");
                serializer.startTag(null, "cid");
                serializer.text("" + courseList.get(j).getCourseNumber());
                serializer.endTag(null, "cid");
                serializer.startTag(null, "steop");
                serializer.text("" + courseList.get(j).getSteop());
                serializer.endTag(null, "steop");
                serializer.startTag(null, "mode");
                serializer.text("" + courseList.get(j).getMode());
                serializer.endTag(null, "mode");
                serializer.startTag(null, "status");
                serializer.text("" + courseList.get(j).getStatus());
                serializer.endTag(null, "status");
                serializer.endTag(null, "course");

            }
            serializer.endTag(null, "courses");
            serializer.endDocument();
            serializer.flush();
            out.close();
            Toast.makeText(context, R.string.save_text_succ,
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void buildSaveFile() {
        final File file = new File(appDir, fileName);
        if (file.exists()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.alert_dialog_curriculum_exists);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    file.delete();
                    saveProcedure();
                    if (saveAndClose) {
                        ((Activity) context).finish();
                    }

                }
            });
            builder.setNegativeButton(R.string.no, null);
            AlertDialog alert = builder.create();
            alert.show();

        }
        else
        {
            saveProcedure();
            if (saveAndClose) {
                ((Activity) context).finish();
            }
        }

    }
}
