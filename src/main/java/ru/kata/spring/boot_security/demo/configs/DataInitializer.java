package ru.kata.spring.boot_security.demo.configs;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleServiceImpl roleService;
    private final UserService userService;
    private final RegistrationService registrationService;
    private final RoleDao roleDao;

    public DataInitializer(@Lazy RoleServiceImpl roleService, UserService userService, RegistrationService registrationService, RoleDao roleDao) {
        this.roleService = roleService;
        this.userService = userService;
        this.registrationService = registrationService;
        this.roleDao = roleDao;
    }


    @Override
    public void run(String[] args) throws Exception {

        if (roleService.findByName("ROLE_USER").isEmpty()) {
            roleService.saveRole(new Role("ROLE_USER"));
        }
        if (roleService.findByName("ROLE_ADMIN").isEmpty()) {
            roleService.saveRole(new Role("ROLE_ADMIN"));
        }

        if (userService.findByUsername("Admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("Admin");
            admin.setEmail("Admin@mail.ru");
            admin.setPassword("admin");
            Role adminRole = roleService.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
            admin.setRoles(Set.of(adminRole));
            registrationService.register(admin);
        }

        if (userService.findByUsername("User").isEmpty()) {
            User basicUser = new User();
            basicUser.setUsername("User");
            basicUser.setEmail("User@mail.ru");
            basicUser.setPassword("user");
            Role userRole = roleService.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
            basicUser.setRoles(Set.of(userRole));
            registrationService.register(basicUser);
        }
    }
}
