package com.example.ihaveadream0528.finalprogarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ihaveadream0528 on 2017/5/23.
 */

public class RFile {
    private String filename;
    private String user;
    private String time;
    private String url;

    public RFile(){

    }
    public RFile(String filename, String user){
        this.filename = filename+".jpg";
        this.user = user;
        this.url = "";
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        this.time = dateFormat.format(new Date().getTime());
    }
    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
