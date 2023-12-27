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

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentById(Long id) {
        return studentRepository.findByIdEquals(id);
    }

    public List<Student> getStudentByName(String name) {
        return studentRepository.findByNameStartingWith(name);
    }

    public List<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmailStartingWith(email);
    }

    public List<Student> getStudentByDob(LocalDate dobAfter, LocalDate dobBefore) {
        if (dobAfter == null && dobBefore == null) {
            throw new IllegalArgumentException();
        }
        if (dobAfter != null && dobBefore == null) {
            return studentRepository.findByDobAfter(dobAfter);
        }
        if (dobAfter == null) {
            return studentRepository.findByDobBefore(dobBefore);
        }

        List<Student> studentsInRange = new ArrayList<>();

        List<Student> studentsAfterStartDate = studentRepository.findByDobAfter(dobAfter);
        List<Student> studentsBeforeEndDate = studentRepository.findByDobBefore(dobBefore);

        for (Student student : studentsAfterStartDate) {
            if (studentsBeforeEndDate.contains(student)) {
                studentsInRange.add(student);
            }
        }

        return studentsInRange;
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
                        "student with id " + studentId + "does not exist"
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
