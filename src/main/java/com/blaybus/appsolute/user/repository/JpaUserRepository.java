package com.blaybus.appsolute.user.repository;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndChangedPassword(String userId, String password);
    Optional<User> findByEmployeeNumber(String employeeNumber);
    Optional<User> findByUserIdAndInitialPassword(String userId, String initialPassword);
    List<User> findByDepartmentGroup(DepartmentGroup departmentGroup);
    void deleteByEmployeeNumber(String employeeNumber);
    Optional<User> findByUserId(String userId);
}
