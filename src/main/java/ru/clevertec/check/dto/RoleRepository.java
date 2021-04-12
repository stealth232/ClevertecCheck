package ru.clevertec.check.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
