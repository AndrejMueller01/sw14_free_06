package com.studyprogress.menu;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studyprogress.R;
import com.studyprogress.MainActivity;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLParser;


public class DeleteMenuCallback implements ActionMode.Callback {
    MainActivity parent;

    public DeleteMenuCallback(MainActivity parentActivity) {
        parent = parentActivity;

    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        XMLParser parser = XMLParser.getInstance(null);

        for (int semCount = 0; semCount < GlobalProperties.SEM_COUNT; semCount++) {

            MainActivity.getAdapters()[semCount].setDelMode(false);

            for (int courseOfCurrSemesterCount = 0; courseOfCurrSemesterCount < MainActivity.getAdapters()[semCount].getCount(); courseOfCurrSemesterCount++) {

                if (MainActivity.getAdapters()[semCount].getDeleteCheckBoxSatus(courseOfCurrSemesterCount) == true) {
                    final String courseName = MainActivity.getCourseListViews()[semCount]
                            .getItemAtPosition(courseOfCurrSemesterCount).toString();
                    Course course = parser.getCourseByNameInList(courseName);
                    parser.deleteCourse(course);
                    String[] courseNames = parser.getCourseNamesWithModesOfSemester(semCount + 1);
                    MainActivity.getAdapters()[semCount].setCourseNamesAfterDeletingAnElement(courseNames, courseOfCurrSemesterCount);
                    courseOfCurrSemesterCount--;
                    parent.setStudyStateChanged();
                }

            }
            MainActivity.getAdapters()[semCount].clearDeleteBoxCheckList();

        }

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        parent.getMenuInflater().inflate(R.menu.delete_menu, menu);
        mode.setTitle(R.string.delete_menu_title);

        for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {

            MainActivity.getAdapters()[i].setDelMode(true);
        }

        return true;
    }



    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        return true;
    }

}