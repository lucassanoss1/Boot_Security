package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RegistrationService registrationService, RoleDao roleDao) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.roleDao = roleDao;
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("editUser/{id}")
    public User editUserForm(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("editUser")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    //@GetMapping("/add")
    //public String addUserForm(Model model) {
        //model.addAttribute("user", new User());
        //return "/add";
    //}

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User userAdd = registrationService.register(user);
        return ResponseEntity.ok(userAdd);
    }
}
