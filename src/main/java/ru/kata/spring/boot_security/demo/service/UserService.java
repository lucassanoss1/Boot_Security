package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    void addUser(String username, String password, String email, Set<String> roleNames);
    List<User> getAllUsers();
    void removeUserById(Long id);
    User updateUser(User user);
    User findUserById(Long id);
    Optional<User> findByUsername(String username);
}
