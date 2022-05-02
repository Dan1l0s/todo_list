package ru.dan1l0s.project.task;

public class Task {

    private int id;
    private int status = 0;
    private String name = "Unknown task";
    private String desc = "Description";
    private String finishby = "01.01.2030";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getFinishby() {
        return finishby;
    }

    public void setFinishby(String finishby) {
        this.finishby = finishby;
    }
}
