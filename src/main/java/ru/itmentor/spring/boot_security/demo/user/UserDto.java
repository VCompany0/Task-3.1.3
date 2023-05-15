package ru.itmentor.spring.boot_security.demo.user;

import ru.itmentor.spring.boot_security.demo.role.RoleEnum;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;

    private String name;

    private String email;

    private int age;

    private String password;
    private List<RoleEnum> roles = new ArrayList<>();

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.age = user.getAge();
        user.getRoles().forEach(role -> this.roles.add(RoleEnum.valueOf(role.getRole())));
    }

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, int age, String password, List<RoleEnum> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }

}
