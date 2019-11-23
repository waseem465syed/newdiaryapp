package com.td.diaryapp.IndividualClasses;

import androidx.annotation.NonNull;

import java.util.List;

public class Contact {

    private int id;
    private String name;
    private List<String> phoneNumbers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
