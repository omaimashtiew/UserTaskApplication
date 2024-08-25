package com.task.TaskManagementApplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User1, Long> {
    Optional<User1> findByUsername(String username);

}