package es.ucm.fdi.tasklist.ui.important;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.DataBaseTask;
import es.ucm.fdi.tasklist.db.TaskDetail;
import es.ucm.fdi.tasklist.ui.ViewTaskActivity;

public class ImportantFragment extends Fragment {

    View view;

    private ArrayList<TaskDetail> importantTaskList = new ArrayList();
    private TaskImportantListAdapter arrayAdapter;
    private ListView taskimportantlistview;

    SQLiteDatabase db;

    public ImportantFragment(){ }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_important,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskimportantlistview = view.findViewById(R.id.listTaskImportantView);
        arrayAdapter = new TaskImportantListAdapter(getContext(), importantTaskList);
        taskimportantlistview.setAdapter(arrayAdapter);
        initDataBase();
        execListener();
    }

    public void initDataBase(){
        DataBaseTask dbHelper = DataBaseTask.getInstance(getContext());
        db = dbHelper.getWritableDatabase();

        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM tasks ORDER BY date ASC", null);
            if (c.moveToFirst()) {
                do {
                    updateList(true,
                            c.getInt(0),
                            (c.isNull(1))? "" : c.getString(1),
                            (c.isNull(2))? "" : c.getString(2),
                            (c.isNull(3))? "" : c.getString(3),
                            c.getInt(4) == 0 ? false : true,
                            c.getInt(5) == 0 ? false : true,
                            (c.isNull(6))? "" : c.getString(6));
                } while (c.moveToNext());
            }
        }
    }

    public TaskDetail updateList(boolean on_off, int _id,  String _title, String _desc, String _date, boolean _fin, boolean _imp, String _hora){
        TaskDetail detail = new TaskDetail(_id, _title, _desc, _date, _fin, _imp, _hora);
        if (on_off) {
            if (importantTaskList.contains(detail)) {
                importantTaskList.remove(detail);
            }
            if(detail.getImp()) importantTaskList.add(detail);
        } else {
            if (importantTaskList.contains(detail)){
                importantTaskList.remove(detail);
                db.execSQL("DELETE FROM tasks WHERE id = " +_id);
            }
            else detail = null;
        }
        arrayAdapter.notifyDataSetChanged();
        return detail;
    }

    private void execListener() {
        taskimportantlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openViewViewNotesActivity(importantTaskList.get(position).getId(), importantTaskList.get(position).getTitle(), importantTaskList.get(position).getDesc(),
                        importantTaskList.get(position).getDate(), importantTaskList.get(position).getFin(), importantTaskList.get(position).getImp(), importantTaskList.get(position).getHora());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void openViewViewNotesActivity(int id, String title, String content, String date, boolean fin, boolean imp, String hora) {
        Intent notesActivityIntent = new Intent(getActivity(), ViewTaskActivity.class);
        notesActivityIntent.putExtra("CREATED",true);
        notesActivityIntent.putExtra("ID",id);
        notesActivityIntent.putExtra("TITLE",title);
        notesActivityIntent.putExtra("CONTENT",content);
        notesActivityIntent.putExtra("DATE",date);
        notesActivityIntent.putExtra("FINISH",fin);
        notesActivityIntent.putExtra("IMPORTANT",imp);
        notesActivityIntent.putExtra("HORA",hora);
        this.startActivityForResult(notesActivityIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String title, content, date, hora;
        boolean finish;
        boolean important;
        int id;

        if(data != null) {
            title = data.getExtras().getString("title");
            content = data.getExtras().getString("content");
            date = data.getExtras().getString("date");
            hora = data.getExtras().getString("hora");
            finish = data.getExtras().getBoolean("finish");
            important = data.getExtras().getBoolean("important");

            if (requestCode == 2) {
                id = data.getExtras().getInt("id");
                if (resultCode == Activity.RESULT_OK) {
                    TaskDetail taskDetail = updateList(true, id, title, content, date, finish, important, hora);
                    DataBaseTask.getInstance(getContext()).updateItem(taskDetail, db);
                }

                if (resultCode == Activity.RESULT_CANCELED) {
                    TaskDetail taskDetail = updateList(false, id, title, content, date, finish, important, hora);
                    DataBaseTask.getInstance(getContext()).deleteItem(taskDetail, db);
                }
            }
        }
    }
}