package com.unifor.cc.arquiteturas.layers.controllers;

import com.unifor.cc.arquiteturas.layers.models.User;
import com.unifor.cc.arquiteturas.layers.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to get all users");
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Received request to get user with id: {}", id);
        Optional<User> user = userService.findById(id);
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
        Optional<User> newUser = userService.save(user);
        if (newUser.isPresent()) {
            return ResponseEntity.ok(newUser.get());
        } else {
            logger.warn("User could not be created: {}", user);
            return ResponseEntity.badRequest().body("User could not be created");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Received request to update user with id: {} and data: {}", id, user);
        user.setId(id);
        Optional<User> updatedUser = userService.update(user);
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
        if (userService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("User not found for deletion with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
