package com.example.api.todoapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task 
{
    @JsonProperty private int id;
    @JsonProperty private String text;
    @JsonProperty private String day;
    @JsonProperty private boolean reminder;

    public Task(@JsonProperty int id, @JsonProperty String text,
                @JsonProperty String day, @JsonProperty boolean reminder)
    {
        this.id = id;
        this.text = text;
        this.day = day;
        this.reminder = reminder;
    }

    public Task()
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
