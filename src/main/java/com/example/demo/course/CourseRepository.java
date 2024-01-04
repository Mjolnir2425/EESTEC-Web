package com.example.demo.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository
        extends JpaRepository<Course, Long> {

    List<Course> findByIdEquals(Long id);

    Optional<Course> findByNameEquals(String name);

    List<Course> findByNameStartingWith(String name);

    List<Course> findByCapacityEquals(Integer capacity);
}