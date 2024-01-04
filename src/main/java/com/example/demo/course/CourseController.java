package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourse(

    ) {
        return courseService.getCourses();
    }

    @PostMapping
    public void registerNewCourse(@RequestBody Course course) {
        courseService.addNewCourse(course);
    }

    @PostMapping(path = "{courseId}")
    public void addStudents(
            @RequestBody List<Long> studentIds,
            @PathVariable ("courseId") Long courseId
    ) {
        courseService.addStudents(courseId, studentIds);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long id) {
        courseService.deleteCourse(id);
    }

    @PutMapping(path = "{courseId}")
    public void updateCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer capacity
    ) {
        courseService.updateCourse(courseId, name, capacity);
    }
}
