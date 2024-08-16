package com.fandresena.learn.model;

public class OneDayModel {
    private String date;
    private boolean inMonth;

    public OneDayModel(String date, boolean inMonth) {
        this.date = date;
        this.inMonth = inMonth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }

    // to string
    @Override
    public String toString() {
        return "{" +
                "\"date\":" +"\""+ date+"\""+
                ", \"inMonth\":" + inMonth +
                '}';
    }
}
