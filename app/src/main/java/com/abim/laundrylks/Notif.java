package com.abim.laundrylks;

import java.util.Date;

public class Notif {
    private String name;
    private String date;

    public Notif() {
    }

    public Notif(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
