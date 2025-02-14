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
    public void addUser(String username, String password, String email, Set<String> roleNames) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
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
    public User updateUser(User user) {
        User userEdit = userDao.findById(user.getId()).orElseThrow();
        userEdit.setUsername(user.getUsername());
        userEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        userEdit.setEmail(user.getEmail());
        Set<Role> rolesUser = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleDao.findByName(role.getName()).orElseThrow(() ->
                    new RuntimeException("Role '" + role.getName() + "' not found"));
            rolesUser.add(existingRole);
        }
        userEdit.setRoles(rolesUser);
        return userDao.save(userEdit);
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
