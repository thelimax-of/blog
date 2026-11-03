package br.com.limax.blog.post.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record PostResponse(UUID id, String title, String content, Instant createdAt, Instant updatedAt) {
}
