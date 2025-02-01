package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDao userDao;
    private final UserDetailService userDetailService;

    @Autowired
    public UserController(UserDao userDao, UserDetailService userDetailService) {
        this.userDao = userDao;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/")
    public String userInfo(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            return "one_user"; // Имя вашего шаблона
        } else {
            return "redirect:/login"; // Перенаправление на страницу логина
        }
    }

}
