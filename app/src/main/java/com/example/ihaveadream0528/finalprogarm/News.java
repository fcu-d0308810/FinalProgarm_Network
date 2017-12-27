package com.example.ihaveadream0528.finalprogarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ihaveadream0528 on 2017/12/25.
 */

public class News {
    private String title;
    private String time;
    private String text;
    public News(){

    }
    public News(String title, String text){
        this.title = title;
        this.text = text;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.time = dateFormat.format(new Date().getTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
