package com.unifor.cc.arquiteturas.layers.services;

import com.unifor.cc.arquiteturas.layers.models.User;
import com.unifor.cc.arquiteturas.layers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public Optional<User> findById(Long id) {
        if (id == null) {
            logger.warn("Attempted to find user with null id");
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    public Optional<User> save(User user) {
        if (user == null) {
            logger.warn("Attempted to save null user");
            return Optional.empty();
        }
        return Optional.of(userRepository.save(user));
    }

}
