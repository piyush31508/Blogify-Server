package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "blog_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost {

    @Id
    private String id;

    private String title;

    private String content;

    private String authorId;

    private String authorName;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}
