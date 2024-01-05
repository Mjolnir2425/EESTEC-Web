package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean exists(Long id) {
        return !getStudent(id).isEmpty();
    }

    public List<StudentDto> getStudents() {
        return Student.getDtos(studentRepository.findAll());
    }

    public List<Student> getStudent(Long id) {
        return studentRepository.findByIdEquals(id);
    }

    public List<StudentDto> getStudentById(Long id) {
        return Student.getDtos(studentRepository.findByIdEquals(id));
    }

    public List<StudentDto> getStudentByName(String name) {
        return Student.getDtos(studentRepository.findByNameStartingWith(name));
    }

    public List<StudentDto> getStudentByEmail(String email) {
        return Student.getDtos(studentRepository.findByEmailStartingWith(email));
    }

    public List<StudentDto> getStudentByDob(LocalDate dobAfter, LocalDate dobBefore) {
        if (dobAfter == null && dobBefore == null) {
            throw new IllegalArgumentException();
        }
        if (dobAfter != null && dobBefore == null) {
            return Student.getDtos(studentRepository.findByDobAfter(dobAfter));
        }
        if (dobAfter == null) {
            return Student.getDtos(studentRepository.findByDobBefore(dobBefore));
        }

        List<Student> studentsInRange = new ArrayList<>();

        List<Student> studentsAfterStartDate = studentRepository.findByDobAfter(dobAfter);
        List<Student> studentsBeforeEndDate = studentRepository.findByDobBefore(dobBefore);

        for (Student student : studentsAfterStartDate) {
            if (studentsBeforeEndDate.contains(student)) {
                studentsInRange.add(student);
            }
        }

        return Student.getDtos(studentsInRange);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), name)) {
            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email unavailable");
            }

            student.setEmail(email);
        }
    }
}
