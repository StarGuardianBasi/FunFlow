package fr.utt.if26.funflow;

public class Category {
    private int id;
    private String name;
    private String icon;

    public Category(){}

    public Category(int id, String name , String icon){
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
