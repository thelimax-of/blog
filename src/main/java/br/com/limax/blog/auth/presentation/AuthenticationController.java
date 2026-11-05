package br.com.limax.blog.auth.presentation;

import br.com.limax.blog.auth.application.AuthorizationService;
import br.com.limax.blog.auth.domain.User;
import br.com.limax.blog.auth.infrastructure.security.TokenService;
import br.com.limax.blog.auth.presentation.dto.LoginRequest;
import br.com.limax.blog.auth.presentation.dto.LoginResponse;
import br.com.limax.blog.auth.presentation.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "Registro e login de usuários")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(
            AuthorizationService authorizationService,
            AuthenticationManager authenticationManager,
            TokenService tokenService
    ) {
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request){
        authorizationService.register(request.username(), request.password());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
