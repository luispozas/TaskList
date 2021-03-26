package es.ucm.fdi.tasklist.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

import es.ucm.fdi.tasklist.R;
import es.ucm.fdi.tasklist.db.DataBaseTask;

public class ViewTaskActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText date;
    EditText hora;
    CheckBox finish;
    CheckBox important;
    Button delete;
    Button save;

    boolean created;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.task_title_edit);
        description = findViewById(R.id.task_description_edit);
        date = findViewById(R.id.task_date_edit);
        hora = findViewById(R.id.task_hour_edit);
        finish = findViewById(R.id.task_finish_edit);
        important = findViewById(R.id.task_important_edit);
        delete = findViewById(R.id.task_delete_edit);
        save = findViewById(R.id.task_save_edit);

        created = getIntent().getExtras().getBoolean("CREATED");
        if(created){
            String t = getIntent().getExtras().getString("TITLE");
            String c = getIntent().getExtras().getString("CONTENT");
            String d = getIntent().getExtras().getString("DATE");
            String h = getIntent().getExtras().getString("HORA");
            boolean f = getIntent().getExtras().getBoolean("FINISH");
            boolean i = getIntent().getExtras().getBoolean("IMPORTANT");

            title.setText(t);
            description.setText(c);
            date.setText(d);
            finish.setChecked(f);
            important.setChecked(i);
            hora.setText(h);

        }
        else{
            date.setText(DataBaseTask.getInstance(this).getDate());
            hora.setText(DataBaseTask.getInstance(this).getHourAndMin());
        }

        execListener();
    }

    private void execListener(){
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(ViewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(DataBaseTask.getInstance(getApplicationContext()).getFormatDate(dayOfMonth, month, year));
                    }
                }, DataBaseTask.getInstance(getApplicationContext()).getAnio(),
                   DataBaseTask.getInstance(getApplicationContext()).getMes(),
                   DataBaseTask.getInstance(getApplicationContext()).getDia());

                dialogoFecha.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialogoHora = new TimePickerDialog(ViewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora.setText(DataBaseTask.getInstance(getApplicationContext()).getFormatHour(hourOfDay, minute));
                    }
                },DataBaseTask.getInstance(getApplicationContext()).getHour(),
                  DataBaseTask.getInstance(getApplicationContext()).getMin(),
                 true);

                dialogoHora.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String c = description.getText().toString();
                boolean f = finish.isChecked();
                String d = date.getText().toString();
                String h = hora.getText().toString();
                boolean i = important.isChecked();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title",t);
                returnIntent.putExtra("content",c);
                returnIntent.putExtra("finish",f);
                returnIntent.putExtra("date",d);
                returnIntent.putExtra("important",i);
                returnIntent.putExtra("hora",h);

                if(created){
                    int id = getIntent().getExtras().getInt("ID");
                    returnIntent.putExtra("id",id);
                }
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                if(created){
                    int id = getIntent().getExtras().getInt("ID");
                    returnIntent.putExtra("id", id);
                }
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });
    }



}