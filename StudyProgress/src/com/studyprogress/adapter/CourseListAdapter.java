package com.studyprogress.adapter;

import java.util.ArrayList;

import com.example.studyprogress.R;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseListAdapter extends BaseAdapter {
	private String[] courses;
	private Context context;
	private int[] colorList;
	private View currentView;

	public CourseListAdapter(String[] courseNames, Context context) {
		this.courses = courseNames;
		this.context = context;
		// TODO
		colorList = new int[30];
		intitializeColorList();
	}

	public int getCount() {
		return courses.length;
	}

	public void intitializeColorList() {
		for (int i = 0; i < 30; i++)
			colorList[i] = 0x00000000;
	}

	public void setViewBackgroundColor(int position, int color) {
		colorList[position] = color;
		notifyDataSetChanged();
	}

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.courses_list_item, null);

		LinearLayout courseListItem = (LinearLayout) view
				.findViewById(R.id.course_list_view_item);

		courseListItem.setBackgroundColor(colorList[position]);
		TextView textViewCourses = (TextView) view
				.findViewById(R.id.courses_text_view);

		textViewCourses.setText(courses[position]);
		currentView = view;

		return view;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return courses[position];
	}

	public int getPositionByString(String name) {
		for (int i = 0; i < courses.length; i++) {
			if (courses[i].equals(name))
				return i;
		}
		return -1;

	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
