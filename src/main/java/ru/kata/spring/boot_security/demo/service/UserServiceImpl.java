package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(User user, Set<String> rolesNames) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> rolesUser = new HashSet<>();
        for (String role : rolesNames) {
            Role roles = roleDao.findByName(role).orElseThrow(() ->
                    new RuntimeException("Role '" + role + "' not found"));
            rolesUser.add(roles);
        }
        user.setRoles(rolesUser);
        userDao.save(user);
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
