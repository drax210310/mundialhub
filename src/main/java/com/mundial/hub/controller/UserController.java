package com.mundial.hub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
public class UserController {

    @GetMapping("/user")
    public Object user(Authentication auth) {
        return auth;
    }
}
