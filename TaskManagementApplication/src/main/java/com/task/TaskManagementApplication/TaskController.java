package com.task.TaskManagementApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;
    
    private User1 getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (User1) authentication.getPrincipal();
        }
        return null;
    }

    
    
    
    public ArrayList<Task> publicTaskList = new ArrayList<>();    
    @GetMapping("/tasks")
    public String listTasks(@RequestParam(name = "status", required = false) String status,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "3") int size,
                            @RequestParam(name = "sort", defaultValue = "dueDate") String sortBy,
                            @RequestParam(name = "order", defaultValue = "asc") String sortOrder,
                            Model model) {


        User1 currentUser = getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        }


        Page<Task> taskPage = taskService.getAllTasks(page, size, sortBy, sortOrder);
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("page", taskPage);
        model.addAttribute("allUsers", userService.getAllUsers());
        return "tasks";
    }
   



    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("allUsers", userService.getAllUsers());
        return "task_form";
    }
    
    @GetMapping("/tasks/{id}")
    public String showTaskForm(@PathVariable("id") Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        if (task == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        return "task-form";  
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task, 
                             @RequestParam("users") Set<Long> userIds, 
                             Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            Set<User1> selectedUsers = userService.getUsersByIds(userIds);
            task.setUsers(selectedUsers);
            taskService.createTask(task, selectedUsers);

            for (User1 user : selectedUsers) {
                System.out.println("Selected user: " + user.getUsername());
            }
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/edit")
    public String updateTask(@PathVariable("id") Long taskId, 
                             @RequestParam("userId") Long userId, 
                             @ModelAttribute("task") Task task, 
                             BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/tasks";
        }
        Task existingTask = taskService.findById(taskId);
        User1 user = userService.findById(userId);

        if (existingTask == null || user == null) {
            return "redirect:/tasks";
        }

        existingTask.getUsers().add(user);
        existingTask.setDescription(task.getDescription());
        taskService.updateTask(existingTask);
        return "redirect:/tasks";
    }








    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            taskService.deleteTask(id);
        }
        return "redirect:/tasks";
    }



    @GetMapping("/tasks/{id}/edit")
    public String showEditTaskForm(@PathVariable("id") Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        if (task == null) {
            return "redirect:/tasks";
        }
        if (task.getUsers() == null) {
            task.setUsers(new  HashSet<>() );
        }
        model.addAttribute("task", task);
        model.addAttribute("allUsers", userService.getAllUsers());
        return "task_form"; 
    }

    

    @GetMapping("/tasks2")
    public String listTasks2(@RequestParam(name = "status", required = false) String status,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "3") int size,
                             @RequestParam(name = "sort", defaultValue = "dueDate") String sortBy,
                             @RequestParam(name = "order", defaultValue = "asc") String sortOrder,
                             Model model, Authentication authentication) {


        User1 currentUser = getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        }

    	
    	
    	 User1 user = getCurrentUser(); 
         if (user != null) {
        	 
             System.out.println("Current User: " + user.getUsername());  

             Page<Task> taskPage;
             if (status != null && !status.isEmpty()) {
                 taskPage = taskService.getTasksByUserAndStatus(user, status, page, size, sortBy, sortOrder);
             } else {
                 taskPage = taskService.getAllTasks(user, page, size, sortBy, sortOrder);
             }
             model.addAttribute("tasks", taskPage.getContent());
             model.addAttribute("page", taskPage);
             model.addAttribute("status", status);
             model.addAttribute("allUsers", userService.getAllUsers());
             model.addAttribute("currentUserName", user.getUsername());
         } else {
             model.addAttribute("tasks", new ArrayList<>());
             model.addAttribute("status", null);
             System.out.println("Current User: Guest");  
         }

        return "loginAsUser";  
    }

}