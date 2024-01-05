package com.example.demo.course;

import com.example.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getCourse(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer capacity
    ) {
        int argCount = 0;
        if (id != null) argCount++;
        if (name != null) argCount++;
        if (capacity != null) argCount++;

        if (argCount > 1) {
            throw new IllegalArgumentException();
        }

        if (id != null) return courseService.getCourseById(id);
        if (name != null) return courseService.getCourseByName(name);
        if (capacity != null) return courseService.getCourseByCapacity(capacity);

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
        if (!courseService.exists(courseId)) {
            throw new IllegalStateException();
        }
        courseService.addStudents(courseId, studentIds);
    }

    @DeleteMapping
    public void deleteCourse(@RequestParam Long courseId) {
        if (!courseService.exists(courseId)) {
            throw new IllegalStateException();
        }
        Course course = courseService.getCourse(courseId).getFirst();
        for (Student student : course.getStudents()) {
            student.removeCourse(course);
        }
        courseService.deleteCourse(courseId);
    }

    @PutMapping
    public void updateCourse(
            @RequestParam Long courseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer capacity
    ) {
        if (!courseService.exists(courseId)) {
            throw new IllegalStateException();
        }
        courseService.updateCourse(courseId, name, capacity);
    }
}
