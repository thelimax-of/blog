package br.com.limax.blog.auth.presentation.dto;

import br.com.limax.blog.auth.domain.UserRole;

public record RegisterRequest(String username, String password, UserRole role) {
}
