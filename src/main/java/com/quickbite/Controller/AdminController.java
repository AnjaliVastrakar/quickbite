package com.quickbite.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.UserDTO;
import com.quickbite.Service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // =========================================================
    // ADMIN DASHBOARD
    // =========================================================

    @GetMapping("/dashboard")
    public String adminDashboard() {

        return "Welcome to Admin Dashboard";
    }

    // =========================================================
    // GET ALL USERS
    // =========================================================

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    // =========================================================
    // GET USER BY ID
    // =========================================================

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    // =========================================================
    // UPDATE USER
    // =========================================================

    @PutMapping("/users/{id}")
    public UserDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO) {

        return userService.updateUser(id, userDTO);
    }

    // =========================================================
    // DELETE USER
    // =========================================================

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }
}