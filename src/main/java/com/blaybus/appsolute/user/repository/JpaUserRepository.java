package com.blaybus.appsolute.user.repository;

import com.blaybus.appsolute.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndChangedPassword(String userId, String password);
    Optional<User> findByEmployeeNumber(String employeeNumber);
    Optional<User> findByUserIdAndInitialPassword(String userId, String initialPassword);
}
