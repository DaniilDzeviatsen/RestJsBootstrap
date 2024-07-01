package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;

public class RoleGA implements GrantedAuthority {

    private Role role;

    public RoleGA(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role.getName();
    }

    public Long getId() {
        return this.role.getId();
    }

    public void setId(Long id) {
        this.role.setId(id);
    }

    public String getName() {
        return this.role.getName();
    }

    public void setName(String name) {
        this.role.setName(name);
    }


}
