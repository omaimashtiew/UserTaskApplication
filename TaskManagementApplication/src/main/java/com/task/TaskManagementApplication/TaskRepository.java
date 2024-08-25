package com.task.TaskManagementApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Page<Task> findByUsersAndStatus(User1 user, String status, Pageable pageable);
    Page<Task> findAll(Pageable pageable);
    Page<Task> findByUsers(User1 user, Pageable pageable);
    

}