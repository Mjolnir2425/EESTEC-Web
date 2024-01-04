package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentController;
import com.example.demo.student.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseById(Long id) {
        return courseRepository.findByIdEquals(id);
    }

    public List<Course> getCourseByName(String name) {
        return courseRepository.findByNameStartingWith(name);
    }

    public List<Course> getCourseByCapacity(Integer capacity) {
        return courseRepository.findByCapacityEquals(capacity);
    }

    public void addNewCourse(Course course) {
        Optional<Course> courseOptional = courseRepository
                .findByNameEquals(course.getName());

        if (courseOptional.isPresent()) {
            throw new IllegalStateException("name taken");
        }

        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);

        if (!exists) {
            throw new IllegalStateException("course with id " + courseId + " does not exist");
        }

        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String name, Integer capacity) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        "course with id " + courseId + " does not exist"
                ));

        if (name != null
                && !name.isEmpty()
                && !Objects.equals(course.getName(), name)) {
            Optional<Course> courseOptional = courseRepository
                    .findByNameEquals(name);

            if (courseOptional.isPresent()) {
                throw new IllegalStateException("name unavailable");
            }

            course.setName(name);
        }

        if (capacity != null
                && !Objects.equals(course.getCapacity(), capacity)) {
            course.setCapacity(capacity);
        }
    }

    public void addStudents(Long courseId, List<Long> studentIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        "course with id " + courseId + " does not exist"
                ));

        for (Long studentId : studentIds) {
            List<Student> students = studentService.getStudentById(studentId);
            if (students.isEmpty()) {
                continue;
            }

            course.addStudent(students.getFirst());
        }
    }
}
