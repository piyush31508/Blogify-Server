package com.example.demo.controller;

import com.example.demo.model.BlogPost;
import com.example.demo.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(service.createPost(post));
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<BlogPost> updatePost(
            @PathVariable String id,
            @RequestBody BlogPost post,
            @RequestParam("uID") String uId) {

        BlogPost result = service.updatePost(id, post, uId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/deletepost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
