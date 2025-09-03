package com.unifor.cc.arquiteturas.layers.services;

import com.unifor.cc.arquiteturas.layers.models.User;
import com.unifor.cc.arquiteturas.layers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public List<User> findAll() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        if (id == null) {
            logger.warn("Attempted to find user with null id");
            return Optional.empty();
        }
        logger.info("Finding user with id: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> save(User user) {
        if (user == null) {
            logger.warn("Attempted to save null user");
            return Optional.empty();
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            logger.warn("Attempted to save user with null or empty name");
            return Optional.empty();
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            logger.warn("Attempted to save user with null or empty email");
            return Optional.empty();
        }
        
        logger.info("Saving user: {}", user);
        try {
            User savedUser = userRepository.save(user);
            logger.info("User saved successfully with id: {}", savedUser.getId());
            return Optional.of(savedUser);
        } catch (Exception e) {
            logger.error("Error saving user: {}", user, e);
            return Optional.empty();
        }
    }

    public Optional<User> update(User user) {
        if (user == null || user.getId() == null) {
            logger.warn("Attempted to update user with null user or null id");
            return Optional.empty();
        }
        
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            logger.warn("User not found for update with id: {}", user.getId());
            return Optional.empty();
        }
        
        logger.info("Updating user with id: {}", user.getId());
        return save(user);
    }

    public boolean deleteById(Long id) {
        if (id == null) {
            logger.warn("Attempted to delete user with null id");
            return false;
        }
        
        if (userRepository.existsById(id)) {
            logger.info("Deleting user with id: {}", id);
            userRepository.deleteById(id);
            return true;
        } else {
            logger.warn("User not found for deletion with id: {}", id);
            return false;
        }
    }
}
