package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/profile")
public class UsersController {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    private final RoleDao roleDao;

    @Autowired
    public UsersController(UserDetailsServiceImpl userDetailsService, UserService userService, RoleDao roleDao) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleDao = roleDao;
    }
//возвращается json юзер принципала
//    @GetMapping("/user")
//    public User getUserById(Principal principal) {
//        String username = principal.getName();
//        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
//
//        return userService.getUser(userPrincipal.getId());
//возвращается json юзера
//    }
//    @GetMapping("/user")
//    public ResponseEntity<User> getUserById(Principal principal) {
//        String username = principal.getName();
//        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
//
//        return new ResponseEntity<>(userService.getUser(userPrincipal.getId()), HttpStatus.OK);
//
//    }

    @GetMapping()
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleDao.findAll(), HttpStatus.OK);
    }

}