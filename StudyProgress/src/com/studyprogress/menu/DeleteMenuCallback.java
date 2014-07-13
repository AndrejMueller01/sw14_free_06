package com.studyprogress.menu;

import com.example.studyprogress.R;
import com.studyprogress.MainActivity;
import com.studyprogress.objects.Course;
import com.studyprogress.properties.GlobalProperties;
import com.studyprogress.tools.XMLParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;


public class DeleteMenuCallback implements ActionMode.Callback {
	MainActivity parent;
	boolean onDelButtonFlag = true;
	public DeleteMenuCallback(MainActivity p) {
		parent = p;
	
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		XMLParser parser = XMLParser.getInstance(null);

		for (int i = 0; i < GlobalProperties.SEM_COUNT; i++) {
			MainActivity.getAdapters()[i].setDelMode(false);
			for(int j = 0; j < MainActivity.getAdapters()[i].getCount(); j++){
				
				if(MainActivity.getAdapters()[i].getDeleteCheckBoxSatus(j) == true){
					final String courseName = MainActivity.getCourseListViews()[i]
							.getItemAtPosition(j).toString();
                    Course course = parser.getCourseByNameInList(courseName);
					parser.deleteCourse(course);
					String[] courseNames = null;
					courseNames = parser.getCourseNamesWithModesOfSemester(i+1);
					MainActivity.getAdapters()[i].setCourseNamesAfterDeletingAnElement(courseNames, j);
					j--;
					parent.setStudyStateChanged();
				}
				
			}
			MainActivity.getAdapters()[i].clearDeleteBoxCheckList();

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
		return false;
	}
	
}