package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<UserDetails> findByUsername(String username);
}
