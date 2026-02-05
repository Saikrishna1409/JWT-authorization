package jwt.security.controller;

import jwt.security.dto.LoginRequest;
import jwt.security.dto.RegisterRequest;
import jwt.security.entity.User;
import jwt.security.repository.UserRepository;
import jwt.security.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthController(UserRepository repo,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil,
                          AuthenticationManager authManager) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(encoder.encode(request.password));
        repo.save(user);
        return "User Registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username, request.password));
        log.info("Login successful for user: {}", request.getUsername());
        return jwtUtil.generateToken(request.username);
    }
}

