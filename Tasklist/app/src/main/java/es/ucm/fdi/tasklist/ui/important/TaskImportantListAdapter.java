package es.ucm.fdi.tasklist.ui.important;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.TaskDetail;

public class TaskImportantListAdapter extends ArrayAdapter<TaskDetail> {
    private Context mContext;

    public TaskImportantListAdapter(Context context , ArrayList<TaskDetail> objects) {
        super(context, -1, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position).getTitle();
        String date = getItem(position).getDate();
        boolean fin = getItem(position).getFin();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.list_item_note,null);

        TextView task_title = convertView.findViewById(R.id.task_title_list);
        TextView task_date = convertView.findViewById(R.id.task_date_list);
        Button category = convertView.findViewById(R.id.task_category_list);

        category.setBackgroundColor(Color.rgb(255, 90, 80));

        convertView.setBackgroundColor(Color.WHITE);
        if(fin){
            convertView.setBackgroundColor(Color.argb(2,244,67,54));
            task_title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        task_title.setText(title);
        task_date.setText(date);

        return convertView;
    }
}
