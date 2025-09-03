package com.unifor.cc.arquiteturas.hexagonal.adapter.out.persistence;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;
import com.unifor.cc.arquiteturas.hexagonal.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(UserPersistenceAdapter.class);
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        logger.info("Finding all users from persistence adapter");
        return userJpaRepository.findAll()
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        logger.info("Finding user by id: {} from persistence adapter", id);
        return userJpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        logger.info("Saving user: {} to persistence adapter", user);
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        User savedUser = userMapper.toDomain(savedEntity);
        logger.info("User saved successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public boolean existsById(Long id) {
        logger.info("Checking if user exists with id: {}", id);
        return userJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with id: {}", id);
        userJpaRepository.deleteById(id);
    }
}