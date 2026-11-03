package br.com.limax.blog.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String title;

    @Size(min = 3, max = 10000)
    @NotBlank
    private String content;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    protected Post(){}

    public Post(String title, String content){
        validateTitle(title);
        validateContent(content);


        this.title = title;
        this.content = content;
        this.createdAt = Instant.now();
    }

    public void update(String title, String content){
        validateTitle(title);
        validateContent(content);

        this.title = title;
        this.content = content;
        this.updatedAt = Instant.now();
    }

    private void validateTitle(String title){
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("Title is required.");
        }

        if(title.length() < 3 || title.length() > 50){
            throw new IllegalArgumentException("Title must be between 3 and 50 characters.");
        }
    }

    private void validateContent(String content){
        if(content == null || content.isBlank()){
            throw new IllegalArgumentException("Content is required.");
        }

        if(content.length() < 3 || content.length() > 10000){
            throw new IllegalArgumentException("Content must be between 3 and 10000 characters.");
        }
    }
}
