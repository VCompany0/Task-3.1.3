package ru.itmentor.spring.boot_security.demo.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.role.Role;
import ru.itmentor.spring.boot_security.demo.role.RoleEnum;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Size(min = 4, max = 100, message = "Имя - от 4 до 100 знаков")
    @Column(nullable = false, unique = true)
    private String name;
    @Size(min = 8, max = 255, message = "Почта - от 5 до 64 символов")
    @Column(nullable = false)
    private String email;

    @Max(255)
    @Column
    private int age;

    @Size(min = 5, max = 64, message = "Пароль должен быть от 5 до 64 символов")
    @Column()
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public User(long id, String name, String email, int age) {
        this(name, email, age);
        this.id = id;
    }

    public User(UserDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.age = dto.getAge();
        this.password = dto.getPassword();
        dto.getRoles().forEach(role -> this.roles.add(new Role(role.name())));
    }

    public User() {

    }

    public User(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.roles = user.getRoles();
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoles(Collection<RoleEnum> roles) {
        this.roles.clear();
        roles.forEach(re -> this.roles.add(new Role(re)));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && name.equals(user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, password, roles);
    }
}
