package br.com.limax.blog.post.presentation;

import br.com.limax.blog.post.application.PostService;
import br.com.limax.blog.post.presentation.dto.PostRequest;
import br.com.limax.blog.post.presentation.dto.PostResponse;
import br.com.limax.blog.post.presentation.mapper.PostMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request){
        var post = PostMapper.toEntity(request);
        var entity = postService.create(post);
        var response = PostMapper.toResponse(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable UUID id){
        var post = postService.findById(id);
        var response = PostMapper.toResponse(post);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(){
        var posts = postService.findAll();
        var response = posts.stream().map(PostMapper::toResponse).toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable UUID id, @Valid @RequestBody PostRequest request){
        var post = postService.update(id, request.title(), request.content());
        var response = PostMapper.toResponse(post);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        postService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
