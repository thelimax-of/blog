package br.com.limax.blog.auth.application;

import br.com.limax.blog.auth.application.exceptions.UserAlreadyExistsException;
import br.com.limax.blog.auth.domain.User;
import br.com.limax.blog.auth.domain.UserRole;
import br.com.limax.blog.auth.infrastructure.persistense.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User register(String username, String rawPassword){
        if(this.userRepository.findByUsername(username) != null){
            throw new UserAlreadyExistsException("Username already exists.");
        }

        var encryptedPassword = passwordEncoder.encode(rawPassword);
        return userRepository.save(new User(username, encryptedPassword, UserRole.USER));
    }
}
