package me.jonas.kviring.bookvault_api.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.jonas.kviring.bookvault_api.Config.JwtService;
import me.jonas.kviring.bookvault_api.User.Role;
import me.jonas.kviring.bookvault_api.User.User;
import me.jonas.kviring.bookvault_api.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
    
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
    .firstname(request.getFirstName())
    .lastname(request.getLastName())
    .email(request.getEmail())
    .password(passwordEncoder.encode(request.getPassword()))
    .role(Role.USER)
    .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
           .token(jwtToken)
           .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      request.getEmail(),request.getPassword())
    );
    var user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalStateException());
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
           .token(jwtToken)
           .build();
  }
}
