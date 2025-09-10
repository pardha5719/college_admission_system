package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int cutoffMarks;

    // Optional: if you use subMarks or getMarks in the template
    private Integer subMarks;

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getCutoffMarks() { return cutoffMarks; }
    public Integer getSubMarks() { return subMarks; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCutoffMarks(int cutoffMarks) { this.cutoffMarks = cutoffMarks; }
    public void setSubMarks(Integer subMarks) { this.subMarks = subMarks; }
}