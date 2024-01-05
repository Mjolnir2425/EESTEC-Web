package com.example.demo;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Bean
    CommandLineRunner commandLineRunner () {
        return args -> {
            Student student1 = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 5)
            );

            Student student2 = new Student(
                    "Franjo",
                    "franjo@gmail.com",
                    LocalDate.of(1999, Month.JANUARY, 5)
            );

            Student student3 = new Student(
                    "Štef",
                    "stef@gmail.com",
                    LocalDate.of(2014, Month.JANUARY, 5)
            );

            Course course1 = new Course(
                    "Hi mom in Brainfuck",
                    69
            );

            Course course2 = new Course(
                    "Java Spring",
                    1
            );

            student1.addCourse(course1);
            student1.addCourse(course2);

            student2.addCourse(course1);

            studentRepository.saveAll(List.of(student1, student2, student3));

            course1.addStudent(student1);
            course1.addStudent(student2);

            course2.addStudent(student2);

            courseRepository.saveAll(List.of(course1, course2));
        };
    }
}
