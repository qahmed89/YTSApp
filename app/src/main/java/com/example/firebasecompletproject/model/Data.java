package com.example.firebasecompletproject.model;

public class Data {
    String id ;
    String title;
    String description;
    String budget ;
    String date;

    public Data() {
    }

    public Data(String id, String title, String description, String budget, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
