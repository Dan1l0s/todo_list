package ru.dan1l0s.project.task;

import java.util.Date;

public class Task implements Comparable{
    private String id;
    private String name;
    private String desc;
    private String time;
    private String date;
    private int status = 0;
    private int duration = 0;

    public Task() {

    }

    public Task(String id, String name, String desc, String time, String date) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    @Override
    public int compareTo(Object o) {
        Task t2 = (Task)o;
        if (this.getStatus() != t2.getStatus())
        {
            return (this.getStatus() > t2.getStatus() ? 1 : -1);
        }
        int tmp1, tmp2;
        tmp1 = Integer.parseInt(this.getDate().substring(6,10));
        tmp2 = Integer.parseInt(t2.getDate().substring(6,10));
        if (tmp1 != tmp2)
        {
            return (tmp1 > tmp2 ? 1 : -1);
        }
        tmp1 = Integer.parseInt(this.getDate().substring(3,5));
        tmp2 = Integer.parseInt(t2.getDate().substring(3,5));
        if (tmp1 != tmp2)
        {
            return (tmp1 > tmp2 ? 1 : -1);
        }
        tmp1 = Integer.parseInt(this.getDate().substring(0,2));
        tmp2 = Integer.parseInt(t2.getDate().substring(0,2));
        if (tmp1 != tmp2)
        {
            return (tmp1 > tmp2 ? 1 : -1);
        }
        tmp1 = Integer.parseInt(this.getTime().substring(0,2));
        tmp2 = Integer.parseInt(t2.getTime().substring(0,2));
        if (tmp1 != tmp2)
        {
            return (tmp1 > tmp2 ? 1 : -1);
        }
        tmp1 = Integer.parseInt(this.getTime().substring(3,5));
        tmp2 = Integer.parseInt(t2.getTime().substring(3,5));
        if (tmp1 != tmp2)
        {
            return (tmp1 > tmp2 ? 1 : -1);
        }
        return 0;
    }

    public boolean compareToDate()
    {
        Date date = new Date();
        int tmp1, tmp2;
        tmp1 = Integer.parseInt(this.getDate().substring(6,10));
        //tmp2 = Integer.parseInt(date.toString().substring(24,28)); // emulator comparator
        tmp2 = Integer.parseInt(date.toString().substring(30,34)); // physical phone
        if (tmp1 < tmp2)
        {
            return true;
        }
        else if (tmp1 > tmp2)
        {
            return false;
        }
        tmp1 = Integer.parseInt(this.getDate().substring(3,5));
        tmp2 = date.getMonth() + 1;
        if (tmp1 < tmp2)
        {
            return true;
        }
        if (tmp1 > tmp2)
        {
            return false;
        }
        tmp1 = Integer.parseInt(this.getDate().substring(0,2));
        tmp2 = date.getDate();
        if (tmp1 < tmp2)
        {
            return true;
        }
        if (tmp1 > tmp2)
        {
            return false;
        }
        tmp1 = Integer.parseInt(this.getTime().substring(0,2));
        tmp2 = date.getHours();
        if (tmp1 < tmp2)
        {
            return true;
        }
        if (tmp1 > tmp2)
        {
            return false;
        }
        tmp1 = Integer.parseInt(this.getTime().substring(3,5));
        tmp2 = date.getMinutes();
        if (tmp1 > tmp2)
        {
            return false;
        }
        return true;
    }
}
