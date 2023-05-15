package ru.itmentor.spring.boot_security.demo.role;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import ru.itmentor.spring.boot_security.demo.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(){

    }

    public Role(String role) {
        this.role = role;
    }

    public Role(RoleEnum roleEnum) {
        this.role = roleEnum.name();
    }


    @Override
    public String getAuthority() {
        return role;
    }

    public void setRole(String authority) {
        this.role = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long role_id) {
        this.id = role_id;
    }

    public String getRole() {
        return role;
    }
}
