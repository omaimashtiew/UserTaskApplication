package com.task.TaskManagementApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching

public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}
