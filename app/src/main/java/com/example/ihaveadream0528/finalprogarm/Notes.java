package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/12/25.
 */

public class Notes {
    private String title;
    private String text;
    public Notes(){

    }
    public Notes(String title, String text){
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
