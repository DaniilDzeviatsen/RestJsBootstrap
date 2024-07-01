package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {


    private final UserDao userRepository;
    private final RoleDao roleDao;

    public DbInit(UserDao userRepository, RoleDao roleDao) {
        this.userRepository = userRepository;
        this.roleDao = roleDao;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleDao.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleDao.save(roleUser);
        Set<Role> roleAdmins = new HashSet<>();
        roleAdmins.add(roleAdmin);
        User admin = new User("admin", "admin", "petrov", roleAdmins);
        Set<Role> roleUsers = new HashSet<>();
        roleUsers.add(roleUser);
        User normalUser = new User("user", "user", "sidorov", roleUsers);
        userRepository.save(admin);
        userRepository.save(normalUser);
    }
}
