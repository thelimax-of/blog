package br.com.limax.blog.post.application;

import br.com.limax.blog.post.application.exceptions.PostNotFoundException;
import br.com.limax.blog.post.domain.Post;
import br.com.limax.blog.post.infrastructure.persistence.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post create(Post post){
        return postRepository.save(post);
    }

    public Post findById(UUID id){
        return postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post not found."));
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post update(UUID id, String title, String content){
        var post = findById(id);

        post.update(title, content);
        return post;
    }

    public void delete(UUID id){
        var post = findById(id);

        postRepository.delete(post);
    }
}
