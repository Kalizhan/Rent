package com.example.rent;

public class Rent {
    String place, day, timebefore, timeafter, reason, group, id, en_time;

    public Rent() {
    }

    public Rent(String place, String en_time, String day, String timebefore, String timeafter, String reason, String group, String id) {
        this.place = place;
        this.en_time = en_time;
        this.day = day;
        this.timebefore = timebefore;
        this.timeafter = timeafter;
        this.reason = reason;
        this.group = group;
        this.id = id;
    }

    public String getEn_time() {
        return en_time;
    }

    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public String getDay() {
        return day;
    }

    public String getTimebefore() {
        return timebefore;
    }

    public String getTimeafter() {
        return timeafter;
    }

    public String getReason() {
        return reason;
    }

    public String getGroup() {
        return group;
    }
}
