package com.company.system.repository;

import com.company.system.controller.ResponseWrapper;
import com.company.system.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByRefId(String refId);
}
