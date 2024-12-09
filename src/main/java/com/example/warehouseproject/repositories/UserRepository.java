package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    boolean existsByEmail(String email);

    Optional<User> findUserByUserId(int id);

    Optional<User> findUserByFirstName(String username);

    Optional<User> findUserByLastName(String username);

    Optional<User> findUserByEmail(String email);

    User findUSerByEmail(String email);

}
