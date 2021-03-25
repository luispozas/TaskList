package es.ucm.fdi.tasklist.ui.home;

import android.os.Parcelable;

public class TaskDetail {
    private String title;
    private String desc;
    private String date;
    private Boolean fin;

    public TaskDetail(String title, String desc, String date, Boolean fin){
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.fin = fin;
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

    public Boolean getFin() {
        return fin;
    }

    public void setFin(Boolean fin) {
        this.fin = fin;
    }

    public boolean equals(Object o) {
        if(o instanceof TaskDetail) {
            TaskDetail taskDetail = (TaskDetail) o;
            return taskDetail.title.equals(this.title) && taskDetail.date.equals(this.date)
                    && taskDetail.desc.equals(this.desc) && taskDetail.fin.equals(this.fin);
        }
        return false;
    }
}
