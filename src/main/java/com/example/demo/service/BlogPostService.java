package com.example.demo.service;

import com.example.demo.model.BlogPost;
import com.example.demo.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository repo;

    public List<BlogPost> getAllPosts() {
        return repo.findAll();
    }

    public BlogPost createPost(BlogPost blogPost) {
        return repo.save(blogPost);
    }

    public Optional<BlogPost> getPostById(String id) {
        return repo.findById(id);
    }

    public BlogPost updatePost(String id, BlogPost updatedPost, String uId) {
        BlogPost existingPost = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + id));

        if (!existingPost.getAuthorId().equals(uId)) {
            throw new RuntimeException("Unauthorized to update this post.");
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        return repo.save(existingPost);
    }


    public void deletePost(String id) {
        repo.deleteById(id);
    }

}
