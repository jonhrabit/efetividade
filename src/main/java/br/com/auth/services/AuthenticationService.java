package br.com.auth.services;

import org.springframework.stereotype.Service;

import br.com.auth.model.Usuario;

@Service
public class AuthenticationService {
  private JwtService jwtService;

  public AuthenticationService(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public String authenticate(Usuario usuario) {
    return jwtService.generateToken(usuario);
  }
}