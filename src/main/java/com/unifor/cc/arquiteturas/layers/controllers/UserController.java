package com.unifor.cc.arquiteturas.layers.controllers;

import com.unifor.cc.arquiteturas.layers.services.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userRepository;

}
