package com.unifor.cc.arquiteturas.hexagonal.domain.port.out;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    boolean existsById(Long id);
    void deleteById(Long id);
}