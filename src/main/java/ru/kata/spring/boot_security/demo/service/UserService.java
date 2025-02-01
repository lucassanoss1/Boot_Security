package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService  {
    public void addUser(User user);
    public List<User> getAllUsers();
    public void removeUserById(Long id);
    public void updateUser(User user);
    public User findUserById(Long id);
}
