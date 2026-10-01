package com.quickbite.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quickbite.DTO.UserDTO;
import com.quickbite.Entity.User;
import com.quickbite.Exception.EmailAlreadyExistsException;
import com.quickbite.Exception.UserNotFoundException;
import com.quickbite.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================================================
    // CREATE USER
    // =========================================================

    public User saveUser(User user) {

        // Check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already exists: " + user.getEmail()
            );
        }

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Every newly registered user gets USER role
        user.setRole("USER");

        return userRepository.save(user);
    }

    // =========================================================
    // GET ALL USERS
    // =========================================================

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone()
                ))
                .toList();
    }

    // =========================================================
    // GET USER BY ID
    // =========================================================

    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }

    // =========================================================
    // UPDATE USER
    // =========================================================

    public UserDTO updateUser(Long id, UserDTO userDTO) {

        // Find existing user
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        // Check whether email belongs to another user
        if (userRepository.countByEmailAndIdNot(
                userDTO.getEmail(), id) > 0) {

            throw new EmailAlreadyExistsException(
                    "Email already exists: "
                    + userDTO.getEmail()
            );
        }

        // Update user details
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        User updatedUser = userRepository.save(user);

        // Return DTO instead of Entity
        return new UserDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getPhone()
        );
    }

    // =========================================================
    // DELETE USER
    // =========================================================

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        userRepository.delete(user);
    }
}