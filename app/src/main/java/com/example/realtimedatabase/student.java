package com.example.realtimedatabase;

public class student {

    private String name;
    private String age;

    // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    public student() {
    }

    public student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
