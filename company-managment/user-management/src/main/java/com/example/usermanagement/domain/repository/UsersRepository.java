package com.example.usermanagement.domain.repository;

import com.example.usermanagement.domain.model.Role;
import com.example.usermanagement.domain.model.User;
import com.example.usermanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository  extends JpaRepository<User, UserId> {
    Optional<User> findByUserName(String userName);
    List<User> findByRole(Role role);

}
