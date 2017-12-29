package com.example.ihaveadream0528.finalprogarm;

/**
 * Created by ihaveadream0528 on 2017/12/27.
 */

public class Absence {
    private String start;
    private String end;
    private String name;
    private String reason;
    public Absence(){

    }
    public Absence(String start, String end, String name, String reason){
        this.start = start;
        this.end = end;
        this.name = name;
        this.reason = reason;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
