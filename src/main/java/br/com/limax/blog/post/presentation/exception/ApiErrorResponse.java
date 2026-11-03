package br.com.limax.blog.post.presentation.exception;

import java.time.Instant;

public record ApiErrorResponse(int status, String error, String message, Instant timestamp) {
}
