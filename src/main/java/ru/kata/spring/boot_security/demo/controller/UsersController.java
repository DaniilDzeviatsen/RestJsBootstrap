package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping()
public class UsersController {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @Autowired
    public UsersController(UserDetailsServiceImpl userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserById(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "user";

    }

}