package es.ucm.fdi.tasklist.ui.calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.DataBaseTask;
import es.ucm.fdi.tasklist.db.TaskDetail;

public class CalendarFragment extends Fragment {

    View view;

    private ArrayList<TaskDetail> taskList = new ArrayList();
    private TaskListCalendarAdapter arrayAdapter;
    private ListView taskListCalendarView;

    private SQLiteDatabase db;
    private String dateSelect;

    public CalendarFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar,container,false);
        taskListCalendarView = view.findViewById(R.id.calendarListView);
        arrayAdapter = new TaskListCalendarAdapter(getContext(), taskList);
        taskListCalendarView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatePickerTimeline datePickerTimeline = getActivity().findViewById(R.id.datePickerTimeline);

        datePickerTimeline.setInitialDate(DataBaseTask.getInstance(getContext()).getAnio(),
                DataBaseTask.getInstance(getContext()).getMes(),
                DataBaseTask.getInstance(getContext()).getDia());

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                taskList.clear();
                arrayAdapter.notifyDataSetChanged();
                dateSelect = DataBaseTask.getInstance(getContext()).getFormatDate(day, month, year);
                initDataBase();
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });

        // Disable date
        //Date[] dates = {Calendar.getInstance().getTime()};
        //datePickerTimeline.deactivateDates(dates);
    }

    public void initDataBase(){
        DataBaseTask dbHelper = DataBaseTask.getInstance(getContext());
        db = dbHelper.getWritableDatabase();

        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM tasks", null);
            if (c.moveToFirst()) {
                do {
                    if(dateSelect.equals((c.isNull(3))? "" : c.getString(3))){
                        updateList(c.getInt(0),
                                  (c.isNull(1))? "" : c.getString(1),
                                  (c.isNull(2))? "" : c.getString(2),
                                  (c.isNull(3))? "" : c.getString(3),
                                  c.getInt(4) == 0 ? false : true,
                                  c.getInt(5) == 0 ? false : true,
                                  (c.isNull(6))? "" : c.getString(6));
                    }
                } while (c.moveToNext());
            }
        }
    }

    public TaskDetail updateList(int _id,  String _title, String _desc, String _date, boolean _fin, boolean _imp, String _hora){
        TaskDetail detail = new TaskDetail(_id, _title, _desc, _date, _fin, _imp, _hora);
        if (!taskList.contains(detail))
            taskList.add(detail);
        arrayAdapter.notifyDataSetChanged();
        return detail;
    }
}
