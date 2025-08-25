package com.unifor.cc.arquiteturas.layers.controllers;

import com.unifor.cc.arquiteturas.layers.models.User;
import com.unifor.cc.arquiteturas.layers.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        logger.info("Received request to create user: {}", user);
        Optional<User> newUser = userService.save(user);
        if (newUser.isPresent()) {
            return ResponseEntity.ok(newUser.get());
        } else {
            logger.warn("User could not be created: {}", user);
            return ResponseEntity.badRequest().body("User could not be created");
        }
    }

}
