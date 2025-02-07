package ru.kata.spring.boot_security.demo.сontroller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String getUserInfo(@AuthenticationPrincipal User user, Model model) {
        User userForm = new User();
        userForm.setId(user.getId());
        userForm.setUsername(user.getUsername());
        userForm.setEmail(user.getEmail());
        model.addAttribute("user", userForm);
        return "one_user";
    }
}
