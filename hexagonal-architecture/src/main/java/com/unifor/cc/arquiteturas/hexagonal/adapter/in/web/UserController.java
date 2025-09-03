package com.unifor.cc.arquiteturas.hexagonal.adapter.in.web;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;
import com.unifor.cc.arquiteturas.hexagonal.domain.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserUseCase userUseCase;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to get all users");
        List<User> users = userUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Received request to get user with id: {}", id);
        Optional<User> user = userUseCase.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("Received request to create user: {}", user);
        try {
            User createdUser = userUseCase.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid user data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Received request to update user with id: {} and data: {}", id, user);
        Optional<User> updatedUser = userUseCase.updateUser(id, user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            logger.warn("User could not be updated with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Received request to delete user with id: {}", id);
        if (userUseCase.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("User not found for deletion with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}