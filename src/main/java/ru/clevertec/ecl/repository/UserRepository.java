package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.repository.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
