package com.studyprogress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studyprogress.R;
import com.studyprogress.properties.GlobalProperties;

public class CourseListAdapter extends BaseAdapter {
    private String[] courses;
    private Context context;
    private int[] colorList;
    private int[] visibilityList;
    private boolean[] deleteBoxCheckList;
    private View currentView;

    static class ViewHolder {
        public CheckBox operationCheckBox;
        public LinearLayout listRowLinearLayout;
        public TextView listRowTextView;
    }

    public CourseListAdapter(String[] courseNames, Context context) {

        this.courses = courseNames;
        this.context = context;

        colorList = new int[GlobalProperties.MAX_COURSES];
        visibilityList = new int[GlobalProperties.MAX_COURSES];
        deleteBoxCheckList = new boolean[GlobalProperties.MAX_COURSES];

        initializeColorList();
        initializeVisibilityList();
        initializeDeleteBoxCheckList();
    }

    public int getCount() {
        return courses.length;
    }

    private void delItemInDeleteBoxCheckList(int pos) {
        for (int i = pos; i < GlobalProperties.MAX_COURSES - 1; i++)
            deleteBoxCheckList[i] = deleteBoxCheckList[i + 1];
    }

    public void initializeColorList() {
        for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
            colorList[i] = 0x00000000;
    }

    public void initializeDeleteBoxCheckList() {
        for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
            deleteBoxCheckList[i] = false;
    }

    public void initializeVisibilityList() {
        for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
            visibilityList[i] = View.INVISIBLE;
    }

    public void setCourseNamesAfterDeletingAnElement(String[] courseNames, int currentPosition) {
        this.courses = courseNames;
        notifyDataSetChanged();
        updateColorList(currentPosition);
        delItemInDeleteBoxCheckList(currentPosition);

    }

    private void updateColorList(int position) {
        for (int i = position; i < GlobalProperties.MAX_COURSES - 1; i++)
            colorList[i] = colorList[i + 1];
    }

    public void setViewBackgroundColor(int position, int color) {
        colorList[position] = color;
        notifyDataSetChanged();
    }

    public boolean getDeleteCheckBoxSatus(int position) {
        return deleteBoxCheckList[position];
    }

    public void clearDeleteBoxCheckList() {
        for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
            deleteBoxCheckList[i] = false;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;

        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.courses_list_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.listRowLinearLayout = (LinearLayout) rowView
                    .findViewById(R.id.course_list_view_item);

            viewHolder.listRowTextView = (TextView) rowView
                    .findViewById(R.id.courses_text_view);

            viewHolder.operationCheckBox = (CheckBox) rowView
                    .findViewById(R.id.course_list_item_checkbox_delete);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.listRowLinearLayout.setBackgroundColor(colorList[position]);
        holder.listRowTextView.setText(courses[position]);
        holder.operationCheckBox.setVisibility(visibilityList[position]);
        holder.operationCheckBox.setTag(position);
        holder.operationCheckBox.setChecked(deleteBoxCheckList[position]);
        holder.operationCheckBox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int rowId = (Integer) v.getTag();
                deleteBoxCheckList[rowId] = checkBox.isChecked();
            }
        });
        currentView = rowView;
        return currentView;
    }

    public Object getItem(int position) {
        return courses[position];
    }

    public int getCoursePosition(String name) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].equals(name))
                return i;
        }
        return -1;

    }

    public long getItemId(int position) {
        return position;
    }

    public void setDelMode(boolean enabled) {
        if (enabled) {
            for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
                visibilityList[i] = View.VISIBLE;
        } else {
            for (int i = 0; i < GlobalProperties.MAX_COURSES; i++)
                visibilityList[i] = View.INVISIBLE;

        }
        notifyDataSetChanged();
    }

}
