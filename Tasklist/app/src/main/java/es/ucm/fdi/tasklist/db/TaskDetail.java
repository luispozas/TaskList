package es.ucm.fdi.tasklist.db;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class TaskDetail implements Parcelable {

    private long id;
    private String title;
    private String desc;
    private String date;
    private String hora;
    private Boolean fin;
    private Boolean imp;

    public TaskDetail(long id, String title, String desc, String date, boolean fin,  boolean imp, String hora){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.fin = fin;
        this.imp = imp;
        this.hora = hora;
    }

    protected TaskDetail(Parcel in) {
        id = in.readLong();
        title = in.readString();
        desc = in.readString();
        date = in.readString();
        hora = in.readString();
        byte tmpFin = in.readByte();
        fin = tmpFin == 0 ? null : tmpFin == 1;
        byte tmpImp = in.readByte();
        imp = tmpImp == 0 ? null : tmpImp == 1;
    }

    public static final Creator<TaskDetail> CREATOR = new Creator<TaskDetail>() {
        @Override
        public TaskDetail createFromParcel(Parcel in) {
            return new TaskDetail(in);
        }

        @Override
        public TaskDetail[] newArray(int size) {
            return new TaskDetail[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Boolean getFin() {
        return fin;
    }

    public void setFin(Boolean fin) {
        this.fin = fin;
    }

    public Boolean getImp() {
        return imp;
    }

    public void setImp(Boolean imp) {
        this.imp = imp;
    }

    public boolean equals(Object o) {
        if(o instanceof TaskDetail) {
            TaskDetail taskDetail = (TaskDetail) o;
            return this.id == taskDetail.id;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(date);
        dest.writeString(hora);
        dest.writeBoolean(fin);
        dest.writeBoolean(imp);
    }
}
