package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    List<Student> findByIdEquals(Long id);

    List<Student> findByNameStartingWith(String name);

    List<Student> findByEmailStartingWith(String email);

    List<Student> findByDobAfter(LocalDate dob);

    List<Student> findByDobBefore(LocalDate dob);
}
