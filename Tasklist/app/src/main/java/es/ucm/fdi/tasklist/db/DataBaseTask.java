package es.ucm.fdi.tasklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Locale;

public class DataBaseTask extends SQLiteOpenHelper {


    private static final String TASK_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT, fin INTEGER, important INTEGER)";
    private static final String DB_NAME = "task.sqlite";
    private static final int DB_VERSION = 1;

    private static DataBaseTask INSTANCE;

    final Calendar calendario = Calendar.getInstance();

    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int dia = calendario.get(Calendar.DAY_OF_MONTH);

    private DataBaseTask(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DataBaseTask getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new DataBaseTask(context);
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int addItem(TaskDetail td, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", td.getTitle().isEmpty()? null:td.getTitle());
        contentValues.put("description", td.getDesc().isEmpty()? null:td.getDesc());
        contentValues.put("date", td.getDate().isEmpty()? null:td.getDate());
        contentValues.put("fin", td.getFin()? 1:0);
        contentValues.put("important", td.getImp()? 1:0);

        db.insert("tasks", null, contentValues);//Items is table name


        Cursor c = db.rawQuery("SELECT * FROM tasks", null);
        if(c.moveToLast()){
            return c.getInt(0);
        }
        return -1;
    }

    public void updateItem(TaskDetail td, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", td.getTitle().isEmpty()? null:td.getTitle());
        contentValues.put("description", td.getDesc().isEmpty()? null:td.getDesc());
        contentValues.put("date", td.getDate().isEmpty()? null:td.getDate());
        contentValues.put("fin", td.getFin()? 1:0);
        contentValues.put("important", td.getImp()? 1:0);

        String whereClause = "id=?";
        String whereArgs[] = {String.valueOf(td.getId())};
        db.update("tasks", contentValues, whereClause, whereArgs);
    }

    public void deleteItem(TaskDetail td, SQLiteDatabase db) {
        String whereClause = "id=?";
        String whereArgs[] = {String.valueOf(td.getId())};
        db.delete("tasks", whereClause, whereArgs);
    }

    public String getFormatDate(int dia, int mes, int anio){
        return String.format(Locale.getDefault(), "%02d/%02d/%02d", dia, mes+1, anio);
    }

    public String getDate(){
        return getFormatDate(dia, mes, anio);
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}

