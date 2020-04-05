package com.kurylchyk.controller;
import java.time.LocalDateTime;

public class TimeCheck {

    private static LocalDateTime from;
    private static LocalDateTime to;

    public void start(){
        from = LocalDateTime.now();
    }

    public void  end(){
        to = LocalDateTime.now();
    }

    public int countOfHours() {
        if(to==null){
            end();
        }
        return (((to.getDayOfYear() - from.getDayOfYear())-1)*24 +(from.getHour() + to.getHour()));
    }

    public String toString(){
        return from.toString();
    }

}
