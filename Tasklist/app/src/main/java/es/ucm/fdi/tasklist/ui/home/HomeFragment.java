package es.ucm.fdi.tasklist.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.ViewTaskActivity;

public class HomeFragment extends Fragment {

    View view;

    private ArrayList<TaskDetail> taskList = new ArrayList();
    private TaskListAdapter arrayAdapter;
    private ListView tasklistView;

    public HomeFragment(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tasklistView = view.findViewById(R.id.listTaskView);
        arrayAdapter = new TaskListAdapter(getContext(), taskList);
        tasklistView.setAdapter(arrayAdapter);
        execListener();
    }

    public void updateList(boolean on_off, String title, String desc, String date, boolean fin){
        TaskDetail detail = new TaskDetail(title, desc, date, fin);
        if (on_off) {
            if (!taskList.contains(detail))
                taskList.add(detail);
        } else {
            if (taskList.contains(detail))
                taskList.remove(detail);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void execListener() {
        tasklistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openViewViewNotesActivity(true, taskList.get(position).getTitle(), taskList.get(position).getDesc(),
                        taskList.get(position).getDate(), taskList.get(position).getFin());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.addNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewViewNotesActivity(false, "","","", true);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public ArrayList<TaskDetail> getTaskList(){
        return this.taskList;
    }

    public void openViewViewNotesActivity(Boolean created, String title, String content, String date, Boolean fin) {
        Intent notesActivityIntent = new Intent(getActivity(), ViewTaskActivity.class);
        if(!created){
            notesActivityIntent.putExtra("CREATED",false);
        }else{
            notesActivityIntent.putExtra("CREATED",true);
            notesActivityIntent.putExtra("TITLE",title);
            notesActivityIntent.putExtra("CONTENT",content);
            notesActivityIntent.putExtra("DATE",date);
            notesActivityIntent.putExtra("FINISH",fin);
        }

        this.startActivityForResult(notesActivityIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String t, c, d;
        boolean f;

        if ((requestCode == 1) && (resultCode == Activity.RESULT_OK)){
            t = data.getStringExtra("t");
            c = data.getStringExtra("c");
            f = data.getBooleanExtra("f", false);
            d = data.getStringExtra("d");
            updateList(true, t, c, d, f);
        }
    }
}