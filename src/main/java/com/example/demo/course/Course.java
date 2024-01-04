package com.example.demo.course;

import com.example.demo.student.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Course {
    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    private Long id;
    private String name;
    private Integer capacity;
    private List<Student> students;

    public Course() {
    }

    public Course(Long id, String name, Integer capacity, List<Student> students) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.students = students;
    }

    public Course(String name, Integer capacity, List<Student> students) {
        this.name = name;
        this.capacity = capacity;
        this.students = students;
    }

    public Course(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", students=" + students +
                '}';
    }
}
