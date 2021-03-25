package es.ucm.fdi.tasklist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class ViewTaskActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button date;
    CheckBox finish;
    Button delete;
    Button save;

    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int dia = calendario.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.task_title_edit);
        description = findViewById(R.id.task_description_edit);
        date = findViewById(R.id.task_date_edit);
        finish = findViewById(R.id.task_finish_edit);
        delete = findViewById(R.id.task_delete_edit);
        save = findViewById(R.id.task_save_edit);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(ViewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(getFecha(dayOfMonth, month, year));
                    }
                }, anio, mes, dia);
                dialogoFecha.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String c = description.getText().toString();
                Boolean f = finish.isChecked();
                String d = date.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("t",t);
                returnIntent.putExtra("c",c);
                returnIntent.putExtra("f",f);
                returnIntent.putExtra("d",d);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        boolean created = getIntent().getExtras().getBoolean("CREATED");
        if(created){
            String t = getIntent().getExtras().getString("TITLE");
            String c = getIntent().getExtras().getString("CONTENT");
            String d = getIntent().getExtras().getString("DATE");
            boolean f = getIntent().getExtras().getBoolean("FINISH");

            title.setText(t);
            description.setText(c);
            date.setText(d);
            finish.setChecked(f);
        }
        else{
            date.setText(getFecha(dia, mes, anio));
        }
    }

    private String getFecha(int dia, int mes, int anio){
        return String.format(Locale.getDefault(), "%02d/%02d/%02d", dia, mes+1, anio);
    }


}