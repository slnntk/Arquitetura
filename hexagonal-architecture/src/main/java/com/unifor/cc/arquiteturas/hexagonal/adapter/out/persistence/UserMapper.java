package com.unifor.cc.arquiteturas.hexagonal.adapter.out.persistence;

import com.unifor.cc.arquiteturas.hexagonal.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(user.getId(), user.getName(), user.getEmail());
    }

    public User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}