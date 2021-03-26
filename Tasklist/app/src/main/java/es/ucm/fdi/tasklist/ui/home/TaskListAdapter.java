package es.ucm.fdi.tasklist.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.TaskDetail;

public class TaskListAdapter extends ArrayAdapter<TaskDetail> {
    private Context mContext;

    public TaskListAdapter(Context context , ArrayList<TaskDetail> objects) {
        super(context, -1, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position).getTitle();
        String date = getItem(position).getDate();
        boolean fin = getItem(position).getFin();
        boolean imp = getItem(position).getImp();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.list_item_note,null);

        TextView task_title = convertView.findViewById(R.id.task_title_list);
        TextView task_date = convertView.findViewById(R.id.task_date_list);

        if(fin) convertView.setBackgroundColor(Color.rgb(232,232,232));

        task_title.setText(title);
        task_date.setText(date);

        return convertView;
    }
}
