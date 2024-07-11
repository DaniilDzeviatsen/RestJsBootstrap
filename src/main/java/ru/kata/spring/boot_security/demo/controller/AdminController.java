package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/users")
public class AdminController {

    private final UserService userService;

    private final UserDetailsServiceImpl userDetailsService;

    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, UserDetailsServiceImpl userDetailsService, RoleDao roleDao) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.roleDao = roleDao;
    }

    @ModelAttribute("listRoles")
    public List<Role> listRoles() {
        return roleDao.findAll();
    }

/*
    @GetMapping("/user")
    public User getUserById(Principal principal, Authentication authentication) {
//        String username = authentication.getName();
//        UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(username);
//        return user;

        String username = principal.getName();
        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);

        return userService.getUser(userPrincipal.getId());}
*/

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable Long id) {

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

//    @GetMapping("/new")
//    public String showSignUpForm(@ModelAttribute("user") User user, Model model, Principal principal) {
//        model.addAttribute("principal", userDetailsService.loadUserByUsername(principal.getName()));
//        return "new";
//    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchElementException("There is no such user");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

