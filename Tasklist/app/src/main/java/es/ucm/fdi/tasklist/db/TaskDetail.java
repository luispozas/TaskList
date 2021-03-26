package es.ucm.fdi.tasklist.db;

public class TaskDetail {

    private int id;
    private String title;
    private String desc;
    private String date;
    private Boolean fin;

    private Boolean imp;

    public TaskDetail(int id, String title, String desc, String date, boolean fin,  boolean imp){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.fin = fin;
        this.imp = imp;
    }

    public int getId() {
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
}
