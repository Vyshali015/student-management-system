package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String course;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks = new ArrayList<>();

    public double getTotal() {
        return marks.stream().mapToDouble(Mark::getScore).sum();
    }

    public double getAverage() {
        return marks.isEmpty() ? 0 : getTotal() / marks.size();
    }

    public String getGrade() {
        double a = getAverage();
        if (marks.isEmpty()) return "-";
        if (a >= 90) return "A+";
        if (a >= 80) return "A";
        if (a >= 70) return "B";
        if (a >= 60) return "C";
        if (a >= 40) return "D";
        return "F";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public List<Mark> getMarks() { return marks; }
    public void setMarks(List<Mark> marks) { this.marks = marks; }
}
