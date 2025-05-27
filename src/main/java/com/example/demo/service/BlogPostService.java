package com.example.demo.service;

import com.example.demo.model.BlogPost;
import com.example.demo.model.User;
import com.example.demo.repo.BlogPostRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository repo;

    @Autowired
    private UserRepository uRepo;

    public List<BlogPost> getAllPosts() {
        return repo.findAll();
    }

    public BlogPost createPost(BlogPost blogPost, String email) {
        // Fetch user by email (from your UserDetailsService or UserRepository)
        Optional<User> userOpt = uRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            blogPost.setAuthorId(user.getId());
            blogPost.setAuthorName(user.getName());
            blogPost.setCreatedAt(LocalDateTime.now());
            return repo.save(blogPost);
        } else {
            throw new RuntimeException("User not found: " + email);
        }
    }


    public Optional<BlogPost> getPostById(String id) {
        return repo.findById(id);
    }

    public BlogPost updatePost(String id, BlogPost updatedPost, String email) {
        BlogPost existingPost = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + id));

        // Fetch current user from DB by email
        Optional<User> userOpt = uRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = userOpt.get();

        // Check if the current user is the owner of the post
        if (!existingPost.getAuthorId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this post.");
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now());

        return repo.save(existingPost);
    }


       public void deletePost(String postId, String email) {
        BlogPost post = repo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        Optional<User> userOpt = uRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = userOpt.get();

        if (!post.getAuthorId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this post.");
        }

        repo.deleteById(postId);
    }

}
