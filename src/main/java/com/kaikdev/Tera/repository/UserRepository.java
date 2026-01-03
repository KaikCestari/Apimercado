package com.kaikdev.Tera.repository;

import com.kaikdev.Tera.model.Entity.User;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

  Boolean  existsByEmail(String email);

    Optional<User> findByEmail(String email);



}
