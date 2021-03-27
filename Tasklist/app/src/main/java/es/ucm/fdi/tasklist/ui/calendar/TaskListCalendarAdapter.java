package es.ucm.fdi.tasklist.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.TaskDetail;

public class TaskListCalendarAdapter extends ArrayAdapter<TaskDetail> {
    private Context mContext;

    public TaskListCalendarAdapter(Context context , ArrayList<TaskDetail> objects) {
        super(context, -1, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position).getTitle();
        String hour = getItem(position).getHora();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.list_item_calendar_note,null);

        TextView task_title = convertView.findViewById(R.id.task_title_calendar_list);
        TextView task_hour = convertView.findViewById(R.id.task_hour_calendar_list);

        task_title.setText(title);
        task_hour.setText(hour);
        return convertView;
    }
}
