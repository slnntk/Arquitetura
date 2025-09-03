package com.unifor.cc.arquiteturas.hexagonal.domain.port.in;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User user);
    boolean deleteUser(Long id);
}