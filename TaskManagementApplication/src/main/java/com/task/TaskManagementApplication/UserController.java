package com.task.TaskManagementApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User1());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User1 user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        String loginType = request.getParameter("loginType");

        if ("admin".equals(loginType)) {
            return "redirect:/admin/tasks"; 
        } else if ("user".equals(loginType)) {
            return "redirect:/user/tasks2"; 
        }

        return "redirect:/login?error"; 
    }
    
}