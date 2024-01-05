package com.example.demo.course;

import com.example.demo.student.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
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
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

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
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public CourseDto getDto() {
        List<Long> studentsIds = new ArrayList<>();
        for (Student student : students) {
            studentsIds.add(student.getId());
        }
        return new CourseDto(name, capacity, studentsIds);
    }

    public static List<CourseDto> getDtos(List<Course> courses) {
        List<CourseDto> dtos = new ArrayList<>();
        for (Course course : courses) {
            dtos.add(course.getDto());
        }
        return dtos;
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
