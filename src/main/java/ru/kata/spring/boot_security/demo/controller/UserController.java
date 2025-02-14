package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;


@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/json")
    public User getUserInfo(@AuthenticationPrincipal User user, Model model) {
        User userForm = new User();
        userForm.setId(user.getId());
        userForm.setUsername(user.getUsername());
        userForm.setEmail(user.getEmail());
        userForm.setRoles(user.getRoles());
        model.addAttribute("authenticationUser", userForm);
        return userForm;
    }

    @GetMapping("/")
    public String getUserInfoPage() {
        return "userRest";
    }
}
