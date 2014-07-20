package com.studyprogress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studyprogress.R;
import com.studyprogress.adapter.CourseListAdapter;
import com.studyprogress.menu.DeleteMenuCallback;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.ActionBarProperties;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.ProgressCalculator;
import com.studyprogress.tools.WebXMLLoader;
import com.studyprogress.tools.XMLParser;
import com.studyprogress.tools.XMLSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends StudyProgressActivity {

    private ProgressBar studyProgressBar;
    private TextView studyProgressPercentage;
    private static CourseListAdapter[] adapters;
    private TextView curriculumNameTextField;
    private TextView semesterTextField;
    private static ListView[] courseListViews;
    private ArrayList<Button> semesterButtons;
    private static boolean studyStateChanged = false;
    private XMLParser parser;
    private int firstTimeOpened;
    private static int curriculumId = 0;
    private static int universityId = 0;
    private boolean isSavedFile = false;
    private boolean parseModeOn = true;
    private static boolean saveModeWithDialog ;

    private static int studMode = 0;
    private static String xmlFileName;
    private static String curriculumName = null;
    private static String universityName = null;

    public static String getXmlFileName() {
        return xmlFileName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBarProperties.standardMainActivityMenu(this);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("firstOpen"))
            firstTimeOpened = extras.getInt("firstOpen");
        if (extras.containsKey("changed"))
            studyStateChanged = extras.getBoolean("changed");
        if (extras.containsKey("xmlFileName"))
            xmlFileName = extras.getString("xmlFileName");

        parser = XMLParser.getInstance(null);

        chooseActivityStartMode();

    }

    private void chooseActivityStartMode() {

        if (firstTimeOpened == GlobalProperties.FIRST_TIME_HERE) {

            curriculumId = parser.getCurrentCurriculum().getCurriculumId();
            universityId = parser.getCurrentUniversity().getId();
            curriculumName = parser.getCurrentCurriculum().getName();
            studMode = parser.getCurrentCurriculum().getMode();
            universityName = parser.getCurrentUniversity().getName();
            parser.clearCurrentCourses();
            parseModeOn = true;
            saveModeWithDialog = true;

            try {
                WebXMLLoader webXMLLoader = new WebXMLLoader(this);
                String xmlFileName = GlobalProperties.COURSE_XML_PREFIX
                        + universityId + "_" + curriculumId;
                webXMLLoader.execute(xmlFileName);

            } catch (NotFoundException ex) {
                // TODO: if file not found
            }

        } else if (firstTimeOpened == GlobalProperties.FROM_OPEN_CURR) {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + GlobalProperties.SAVE_FILE_DIR,
                    getXmlFileName()
            );

            InputStream fileInputStream = null;

            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            parser.setInputStream(fileInputStream);
            isSavedFile = true;
            parser.parseCourses(isSavedFile);

            curriculumId = parser.getCurrentCurriculum().getCurriculumId();
            curriculumName = parser.getCurrentCurriculum().getName();
            universityName = parser.getCurrentUniversity().getName();
            saveModeWithDialog = false;
            studMode = parser.getCurrentCurriculum().getMode();
            parseModeOn = false;
            initComponents();

        } else if(firstTimeOpened == GlobalProperties.FROM_ADDING_COURSES){
            curriculumId = parser.getCurrentCurriculum().getCurriculumId();
            curriculumName = parser.getCurrentCurriculum().getName();
            universityName = parser.getCurrentUniversity().getName();
            studMode = parser.getCurrentCurriculum().getMode();
            isSavedFile = false;
            parseModeOn = false;
            initComponents();
        }
    }

    @Override
    public void initComponents() {
        if (parseModeOn)
            parser.parseCourses(isSavedFile);

        studyProgressBar = (ProgressBar) findViewById(R.id.study_progress_bar);
        semesterButtons = new ArrayList<Button>();

        studyProgressPercentage = (TextView) findViewById(R.id.progress_text_view);
        curriculumNameTextField = (TextView) findViewById(R.id.curriculumNameInMainActivityTextView);
        curriculumNameTextField.setText(universityName + " - " +curriculumName);

        setCourseListViews(new ListView[GlobalProperties.SEM_COUNT]);
        setAdapters(new CourseListAdapter[GlobalProperties.SEM_COUNT]);

        semesterTextField = (TextView) findViewById(R.id.semester_line_description_text_view);
        getCourseListViews()[0] = (ListView) findViewById(R.id.courses_list_view_sem1);
        getCourseListViews()[1] = (ListView) findViewById(R.id.courses_list_view_sem2);
        getCourseListViews()[2] = (ListView) findViewById(R.id.courses_list_view_sem3);
        getCourseListViews()[3] = (ListView) findViewById(R.id.courses_list_view_sem4);
        getCourseListViews()[4] = (ListView) findViewById(R.id.courses_list_view_sem5);
        getCourseListViews()[5] = (ListView) findViewById(R.id.courses_list_view_sem6);
        getCourseListViews()[6] = (ListView) findViewById(R.id.courses_list_view_opt_courses);

        semesterButtons.add((Button) findViewById(R.id.semester_1_name_button));
        semesterButtons.add((Button) findViewById(R.id.semester_2_name_button));
        semesterButtons.add((Button) findViewById(R.id.semester_3_name_button));
        semesterButtons.add((Button) findViewById(R.id.semester_4_name_button));
        semesterButtons.add((Button) findViewById(R.id.semester_5_name_button));
        semesterButtons.add((Button) findViewById(R.id.semester_6_name_button));
        semesterButtons
                .add((Button) findViewById(R.id.semester_optional_courses));

        if (studMode == GlobalProperties.DIPL_STUD) {
            for (int i = 3; i < semesterButtons.size() - 1; i++)
                semesterButtons.get(i).setVisibility(View.INVISIBLE);
            semesterTextField.setText("Abschnitt");
        } else if (studMode == GlobalProperties.MAST_STUD) {
            for (int i = 4; i < semesterButtons.size() - 1; i++)
                semesterButtons.get(i).setVisibility(View.INVISIBLE);
        } else if (studMode == GlobalProperties.LA_STUD) {
            // TODO:+3 Sem Buttons
        }

        refreshProgress();

        String[][] courseNamesInList = null;
        courseNamesInList = new String[GlobalProperties.SEM_COUNT][];

        for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
            courseNamesInList[i] = parser.getCourseNamesWithModesOfSemester(i + 1);

        for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
            getAdapters()[i] = new CourseListAdapter(courseNamesInList[i], this);

        for (int i = 0; i < GlobalProperties.SEM_COUNT; i++)
            getCourseListViews()[i].setAdapter(getAdapters()[i]);

        setClickListeners();
        setBackgroundColors();

    }

    private void setBackgroundColors() {
        for (int j = 0; j < GlobalProperties.SEM_COUNT; j++)
            for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
                String courseNameInList = parser
                        .getCurrentCourses().get(i).getCourseName() + " " + parser.getCurrentCourses().get(i).getMode();
                if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_DONE) {
                    int position = getAdapters()[j].getCoursePosition(courseNameInList);
                    if (position != -1)
                        getAdapters()[j].setViewBackgroundColor(position,
                                Color.GREEN);
                } else if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_IN_PROGRESS) {
                    int position = getAdapters()[j].getCoursePosition(courseNameInList);
                    if (position != -1)
                        getAdapters()[j].setViewBackgroundColor(position,
                                Color.YELLOW);
                } else if (parser.getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_TO_DO) {

                    int position = getAdapters()[j].getCoursePosition(courseNameInList);
                    if (position != -1)
                        getAdapters()[j].setViewBackgroundColor(position,
                                Color.RED);
                }
            }
    }

    private void setClickListeners() {
        for (int i = 0; i < semesterButtons.size(); i++) {
            semesterButtons.get(i).setOnTouchListener(setupOnTouchListeners(i));
        }

        for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {
            getCourseListViews()[i]
                    .setOnItemClickListener(setupOnItemClickListener(i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save_item:
                saveCourse(false);
                return true;
            case R.id.delete_item:
                DeleteMenuCallback dmc = new DeleteMenuCallback(this);
                startActionMode(dmc);
                return true;
            case R.id.add_item:
                Intent intent = new Intent(MainActivity.this,
                        CreateOptionalCoursesActivity.class);
                startActivity(intent);
                finish();
                return true;

        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void saveCourse(boolean saveAndClose) {
        studyStateChanged = false;

        XMLSave saver = new XMLSave(parser.getCurrentCourses(),this);
        if(!saveModeWithDialog)
            saver.setFileName(getXmlFileName());
        saver.saveXML( saveAndClose, saveModeWithDialog, universityName, curriculumName, curriculumId, studMode);



    }

    public OnTouchListener setupOnTouchListeners(final int semester) {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
                    for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {
                        if (i == semester) {
                            semesterButtons.get(i).setPressed(true);
                            getCourseListViews()[i].setVisibility(View.VISIBLE);
                        } else {
                            semesterButtons.get(i).setPressed(false);

                            getCourseListViews()[i]
                                    .setVisibility(View.INVISIBLE);
                        }

                        getCourseListViews()[i].refreshDrawableState();
                    }
                    return true;

                }
                return false;
            }
        };
    }

    //TODO: class for AlertDialog + no hardcoded stuff
    public OnItemClickListener setupOnItemClickListener(final int semester) {
        return new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                studyStateChanged = true;

                final String courseNameInList = getCourseListViews()[semester]
                        .getItemAtPosition(position).toString();
                final Course currentCourse = parser.getCourseByNameInList(courseNameInList);

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);

                builder.setTitle(courseNameInList);

                String courseInformation = "ECTS: ";
                float courseEcts = parser.getEctsByCourse(currentCourse);
                String ectsString = Float.valueOf(courseEcts).toString();
                courseInformation += ectsString + "\n";
                courseInformation += "Kursnummer: ";
                courseInformation += parser.getCourseNumberByCourse(currentCourse);
                courseInformation += "\n";
                courseInformation += "Steop: ";
                int courseSteop = parser.getCourseSteopByCourse(currentCourse);
                if (courseSteop == 1) {
                    courseInformation += "Ja\n";
                } else
                    courseInformation += "Nein\n";

                courseInformation += "Modus: ";
                courseInformation += parser.getCourseModeByCourse(currentCourse);
                courseInformation += "\n";

                builder.setMessage(courseInformation);
                builder.setPositiveButton(R.string.done,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                getAdapters()[semester].setViewBackgroundColor(
                                        position, Color.GREEN);
                                setProgressOfCourseDone(currentCourse);

                            }
                        }
                );

                builder.setNegativeButton(R.string.todo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                getAdapters()[semester].setViewBackgroundColor(
                                        position, Color.RED);

                                setProgressOfCourseTodo(currentCourse);

                            }
                        }
                );

                builder.setNeutralButton(R.string.inprogress,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                getAdapters()[semester].setViewBackgroundColor(
                                        position, Color.YELLOW);

                                setProgressOfCourseInProgress(currentCourse);
                            }
                        }
                );

                AlertDialog alert = builder.create();
                alert.show();

            }
        };
    }

    public void refreshProgress() {
        ProgressCalculator calculator = new ProgressCalculator(parser);
        float currentEcts = calculator.calcuateCurrentECTS();
        int maxECTS = calculator.getMaxECTS(GlobalProperties.STATIC_ECTS_MODE);

        studyProgressBar.setMax(maxECTS);
        studyProgressBar.setProgress((int) currentEcts);
        studyProgressBar.refreshDrawableState();

        float progressInPercent = calculator.calculatePercentage();

        studyProgressPercentage.setText(currentEcts + "/" + maxECTS + " ECTS ("
                + progressInPercent + "%)");
    }

    private void setProgressOfCourseDone(Course course) {
        for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
            if (parser.getCurrentCourses().get(i).equals(course))
                parser.setStatusOfCurrentCourseTo(i,
                        GlobalProperties.STATUS_DONE);
        }

        refreshProgress();

    }

    public void setStudyStateChanged() {
        studyStateChanged = true;
    }

    private void setProgressOfCourseTodo(Course course) {
        for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
            if (parser.getCurrentCourses().get(i).equals(course))
                parser.setStatusOfCurrentCourseTo(i,
                        GlobalProperties.STATUS_TO_DO);
        }

        refreshProgress();

    }

    private void setProgressOfCourseInProgress(Course course) {
        for (int i = 0; i < parser.getCurrentCourses().size(); i++) {
            if (parser.getCurrentCourses().get(i)
                    .equals(course))
                parser.setStatusOfCurrentCourseTo(i,
                        GlobalProperties.STATUS_IN_PROGRESS);
        }

        refreshProgress();

    }


    @Override
    public void onBackPressed() {
        if (studyStateChanged) {
            studyStateChanged = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.changes_save)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    saveCourse(true);
                                }
                            }
                    )
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    MainActivity.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            super.onBackPressed();
        }
    }

    public static ListView[] getCourseListViews() {
        return courseListViews;
    }
    public static void setCourseListViews(ListView[] courseListViews) {
        MainActivity.courseListViews = courseListViews;
    }

    public static CourseListAdapter[] getAdapters() {
        return adapters;
    }

    public static void setAdapters(CourseListAdapter[] adapters) {
        MainActivity.adapters = adapters;
    }



}
