package com.example.studentmanagement;

public class Student {
    private final int id;
    private final String name;
    private final String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-30s %s", id, name, email);
    }
}
