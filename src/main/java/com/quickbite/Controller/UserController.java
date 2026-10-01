package com.quickbite.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.RegisterUserDTO;
import com.quickbite.DTO.UserDTO;
import com.quickbite.Entity.User;
import com.quickbite.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody RegisterUserDTO registerUserDTO) {

        User user = new User();

        user.setName(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPhone(registerUserDTO.getPhone());
        user.setPassword(registerUserDTO.getPassword());

        User savedUser = userService.saveUser(user);

        UserDTO response = new UserDTO(
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // Get All Users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get User By ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id) {

        UserDTO user = userService.getUserById(id);

        return new ResponseEntity<>(
                user,
                HttpStatus.OK
        );
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {

        UserDTO updatedUser = userService.updateUser(id, userDTO);

        return new ResponseEntity<>(
                updatedUser,
                HttpStatus.OK
        );
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}