package com.example.Auth_Demo.Controllers;

import com.example.Auth_Demo.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @GetMapping("/tasks")
    public Object getTasks(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " from the token
        }

        if (jwtUtil.isValidToken(token)) {
            return new String[]{"Task 1", "Task 2", "Task 3"};
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}
