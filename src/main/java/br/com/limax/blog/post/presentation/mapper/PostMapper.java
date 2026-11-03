package br.com.limax.blog.post.presentation.mapper;

import br.com.limax.blog.post.domain.Post;
import br.com.limax.blog.post.presentation.dto.PostRequest;
import br.com.limax.blog.post.presentation.dto.PostResponse;

public final class PostMapper {
    private PostMapper(){}

    public static Post toEntity(PostRequest request){
        return new Post(request.title(), request.content());
    }

    public static PostResponse toResponse(Post post){
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
