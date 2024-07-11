package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.exceptions.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

   private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleDao.findByName(role.getName()))
                .collect(Collectors.toSet()));
        user.setPassword(user.getPassword());
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
//        userDao.save(user);
        User updateUser = userDao.findById(id).orElseThrow(() -> new NoSuchUserException(
                "There is no employee with ID = '" + id + "' in Database"
        ));
        updateUser.setUsername(user.getUsername());
        updateUser.setSurname(user.getSurname());

        if (!user.getPassword().equals(updateUser.getPassword())) {
            updateUser.setPassword(user.getPassword());
        }
        updateUser.setRoles(user.getRoles().stream()
                .map(role -> roleDao.findByName(role.getName()))
                .collect(Collectors.toSet()));

        userDao.save(updateUser);
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
