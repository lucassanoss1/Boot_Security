package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleDao roledao;

    public DataInitializer(RoleDao roledao) {
        this.roledao = roledao;
    }

    @Override
    public void run(String[] args) throws Exception {

        if (roledao.findByName("ROLE_USER").isEmpty()) {
            roledao.save(new Role("ROLE_USER"));
        }
        if (roledao.findByName("ROLE_ADMIN").isEmpty()) {
            roledao.save(new Role("ROLE_ADMIN"));
        }
    }

}
