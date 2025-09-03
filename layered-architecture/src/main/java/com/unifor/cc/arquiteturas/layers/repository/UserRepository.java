package com.unifor.cc.arquiteturas.layers.repository;

import com.unifor.cc.arquiteturas.layers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
