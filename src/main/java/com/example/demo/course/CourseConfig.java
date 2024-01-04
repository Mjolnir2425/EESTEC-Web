package com.example.demo.course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class CourseConfig {
    @Bean
    CommandLineRunner commandLineRunner(CourseRepository repository) {
        return args -> {
            Course hiMom = new Course(
                    "Hi mom in Brainfuck",
                    69
            );

            repository.saveAll(
                    List.of(hiMom)
            );
        };
    }
}
