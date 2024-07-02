package com.velostore.repo;

import com.velostore.model.User;
import com.velostore.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(Role role);
    User findByRole(Role role);
}
