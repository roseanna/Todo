package com.example.roseanna.todo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by roseanna on 2/25/16.
 */
public class ToDo implements Serializable {
    private String title;
    private Date date;
    private String description;
    private boolean selected;

    public ToDo(String title, String description){
        this.title = title;
        this.description = description;
        Date d = new Date();
        this.date = d;
        this.selected = false;
    }
    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void set() {
        this.selected = true;
    }
    public void unset(){
        this.selected = false;
    }
    public boolean isSelected(){return selected;}

}
