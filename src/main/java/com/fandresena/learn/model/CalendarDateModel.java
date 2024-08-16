package com.fandresena.learn.model;

import java.util.List;

public class CalendarDateModel {

    private int year;
    private int month;
    private List<OneDayModel> days;

    public CalendarDateModel(int year, int month, List<OneDayModel> days){
        this.year = year;
        this.month = month;
        this.days = days;
    }

    //to String
    @Override
    public String toString(){
        return "{" +
                "\"year\":" + year +
                ", \"month\":" + month +
                ", \"calendarDays\":[" + this.displayDays() +"]"+
                '}';
    }

    //display days
    public String displayDays(){
        StringBuilder sb = new StringBuilder();
        for(OneDayModel day : days){
            sb.append(day.toString()).append(", ");
        }
        return sb.toString().substring(0, sb.length()-2);
    }
    
}
