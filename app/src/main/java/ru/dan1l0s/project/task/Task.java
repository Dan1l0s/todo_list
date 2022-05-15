package ru.dan1l0s.project.task;

public class Task {
    private String id;
    private String name;
    private String desc;
    private String time;
    private String date;

    public Task() {

    }

    public Task(String id, String name, String desc, String time, String date) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.date = date;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
