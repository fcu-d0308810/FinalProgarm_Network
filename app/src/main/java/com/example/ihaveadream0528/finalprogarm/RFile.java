package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/5/23.
 */

public class RFile {
    private String filename;
    private String user;
    private String url;

    public RFile(){

    }
    public RFile(String filename, String user, String url){
        this.filename = filename+".jpg";
        this.user = user;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
