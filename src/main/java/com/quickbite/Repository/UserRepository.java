package com.quickbite.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quickbite.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.id <> :id")
    long countByEmailAndIdNot(
            @Param("email") String email,
            @Param("id") Long id);

    Optional<User> findByEmail(String email);
}