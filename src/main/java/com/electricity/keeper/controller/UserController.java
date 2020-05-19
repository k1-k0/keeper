package com.electricity.keeper.controller;

import com.electricity.keeper.repository.UserRepository;
import com.electricity.keeper.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotBlank;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Hi," + userService.getCurrentUsername());
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        // FIXME: Password sending as it is!
        if (userService.verifyUser(username, password)) {
            return ResponseEntity.ok("Login success!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect user credentials!");
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@NotBlank @RequestParam String username,
                                      @NotBlank @RequestParam String password,
                                      @NotBlank @RequestParam String confirm,
                                      @NotBlank @RequestParam String email) {
        // FIXME: Password sending as it is!
        try {
            var user = userService.registerUser(username, password, confirm, email);
            return ResponseEntity.ok("Register success!");
        } catch (EntityExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Register failure");
        }
    }
}
