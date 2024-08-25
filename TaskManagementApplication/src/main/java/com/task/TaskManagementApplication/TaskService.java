package com.task.TaskManagementApplication;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    
    public Task createTask(Task task, Set<User1> users) {
        task.setUsers(users);
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
    	if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            task.setDescription("cc");
        }
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    
    public Page<Task> getAllTasks(User1 user, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return taskRepository.findByUsers(user, pageable); 
    }
    
    public Page<Task> getTasksByUserAndStatus(User1 user, String status, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return taskRepository.findByUsersAndStatus(user, status, pageable);
    }
    
    public Page<Task> getTasksByUser(User1 user, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return taskRepository.findByUsers(user, pageable);
    }
    public Page<Task> getAllTasks(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return taskRepository.findAll(pageable);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                             .orElse(null); // or throw an exception if preferred
    }
    
}

