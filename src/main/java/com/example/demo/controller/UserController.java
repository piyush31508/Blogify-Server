package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/newuser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = service.createUser(user);
        System.out.println(createdUser);
        return ResponseEntity.ok(createdUser);
    }



    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String email) {
        User user = service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
