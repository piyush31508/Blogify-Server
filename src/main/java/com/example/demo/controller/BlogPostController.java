package com.example.demo.controller;

import com.example.demo.model.BlogPost;
import com.example.demo.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin(origins = "*")
public class BlogPostController {

    @Autowired
    private BlogPostService service;


    @GetMapping("/api/posts")
    public ResponseEntity<List<BlogPost>> getAllPosts() {
        return ResponseEntity.ok(service.getAllPosts());
    }

    @GetMapping("/api/getpost/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable String id) {

        Optional<BlogPost> post = service.getPostById(id);
        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/createpost")
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // This will return the username/email from Basic Auth

        BlogPost savedPost = service.createPost(post, email);
        return ResponseEntity.ok(savedPost);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<BlogPost> updatePost(
            @PathVariable String id,
            @RequestBody BlogPost post) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Extract the authenticated user's email

        BlogPost result = service.updatePost(id, post, email);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/deletepost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // get logged-in user's email

        try {
            service.deletePost(id, email);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
    }

}
