package es.ucm.fdi.tasklist.db;

public class Categories{
    private int color;
    private String type;

    public Categories(String type, int color) {
        this.color = color;
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return type.equals(((Categories) o).getType());
    }

    @Override
    public String toString() {
        return type;
    }
}

