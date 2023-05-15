package ru.itmentor.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.role.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String name);
}
