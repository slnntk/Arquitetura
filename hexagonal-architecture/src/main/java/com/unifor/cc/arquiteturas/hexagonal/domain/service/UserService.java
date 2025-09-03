package com.unifor.cc.arquiteturas.hexagonal.domain.service;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;
import com.unifor.cc.arquiteturas.hexagonal.domain.port.in.UserUseCase;
import com.unifor.cc.arquiteturas.hexagonal.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> getAllUsers() {
        logger.info("Getting all users from domain service");
        return userRepositoryPort.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user id: {}", id);
            return Optional.empty();
        }
        logger.info("Getting user by id: {}", id);
        return userRepositoryPort.findById(id);
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            logger.warn("Attempted to create null user");
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!user.isValid()) {
            logger.warn("Attempted to create invalid user: {}", user);
            throw new IllegalArgumentException("User data is invalid");
        }
        
        logger.info("Creating user: {}", user);
        User savedUser = userRepositoryPort.save(user);
        logger.info("User created successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user id for update: {}", id);
            return Optional.empty();
        }
        if (user == null || !user.isValid()) {
            logger.warn("Invalid user data for update: {}", user);
            return Optional.empty();
        }
        
        if (!userRepositoryPort.existsById(id)) {
            logger.warn("User not found for update with id: {}", id);
            return Optional.empty();
        }
        
        user.setId(id);
        logger.info("Updating user with id: {}", id);
        User updatedUser = userRepositoryPort.save(user);
        return Optional.of(updatedUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user id for deletion: {}", id);
            return false;
        }
        
        if (!userRepositoryPort.existsById(id)) {
            logger.warn("User not found for deletion with id: {}", id);
            return false;
        }
        
        logger.info("Deleting user with id: {}", id);
        userRepositoryPort.deleteById(id);
        return true;
    }
}