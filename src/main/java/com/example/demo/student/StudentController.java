package com.example.demo.student;

import com.example.demo.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getStudent(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobBefore
    ) {
        int argCount = 0;
        if (id != null) argCount++;
        if (name != null) argCount++;
        if (email != null) argCount++;
        if (dobAfter != null) argCount++;
        if (dobBefore != null) argCount++;

        if (argCount > 1 && !(argCount == 2 && dobAfter != null && dobBefore != null)) {
            throw new IllegalArgumentException();
        }

        if (id != null) return studentService.getStudentById(id);
        if (name != null) return  studentService.getStudentByName(name);
        if (email != null) return studentService.getStudentByEmail(email);
        if (dobAfter != null || dobBefore != null) return studentService.getStudentByDob(dobAfter, dobBefore);

        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping
    public void deleteStudent(@RequestParam Long studentId) {
        if (!studentService.exists(studentId)) {
            throw new IllegalStateException();
        }
        Student student = studentService.getStudent(studentId).getFirst();
        for (Course course : student.getCourses()) {
            course.removeStudent(student);
        }
        studentService.deleteStudent(studentId);
    }

    @PutMapping
    public void updateStudent(
            @RequestParam Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        if (!studentService.exists(studentId)) {
            throw new IllegalStateException();
        }
        studentService.updateStudent(studentId, name, email);
    }
}
