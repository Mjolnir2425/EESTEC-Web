package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentController;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public boolean exists(Long id) {
        return !getCourse(id).isEmpty();
    }

    public List<CourseDto> getCourses() {
        return Course.getDtos(courseRepository.findAll());
    }

    public List<Course> getCourse(Long id) {
        return courseRepository.findByIdEquals(id);
    }

    public List<CourseDto> getCourseById(Long id) {
        return Course.getDtos(courseRepository.findByIdEquals(id));
    }

    public List<CourseDto> getCourseByName(String name) {
        return Course.getDtos(courseRepository.findByNameStartingWith(name));
    }

    public List<CourseDto> getCourseByCapacity(Integer capacity) {
        return Course.getDtos(courseRepository.findByCapacityEquals(capacity));
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
            List<Student> students = studentService.getStudent(studentId);
            if (students.isEmpty()) {
                continue;
            }
            Student student = students.getFirst();

            if (student.getCourses().contains(course)) {
                continue;
            }

            course.addStudent(student);
            student.addCourse(course);
        }
    }
}
