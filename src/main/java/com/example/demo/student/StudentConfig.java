package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 5)
            );

            Student franjo = new Student(
                    "Franjo",
                    "franjo@gmail.com",
                    LocalDate.of(1999, Month.JANUARY, 5)
            );

            Student stef = new Student(
                    "Štef",
                    "stef@gmail.com",
                    LocalDate.of(2014, Month.JANUARY, 5)
            );

            repository.saveAll(
                    List.of(alex, franjo, stef)
            );
        };
    }
}
