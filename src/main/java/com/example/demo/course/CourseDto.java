package com.example.demo.course;

import java.util.List;

public class CourseDto {
    private String name;
    private Integer capacity;
    private List<Long> studentsIds;

    public CourseDto(String name, Integer capacity, List<Long> studentsIds) {
        this.name = name;
        this.capacity = capacity;
        this.studentsIds = studentsIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Long> getStudentsIds() {
        return studentsIds;
    }

    public void setStudentsIds(List<Long> studentsIds) {
        this.studentsIds = studentsIds;
    }
}
