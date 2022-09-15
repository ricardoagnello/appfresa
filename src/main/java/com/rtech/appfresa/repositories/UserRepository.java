package com.rtech.appfresa.repositories;

import com.rtech.appfresa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByLogin(String login);
}