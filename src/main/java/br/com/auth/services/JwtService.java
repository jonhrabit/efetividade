package br.com.auth.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.com.auth.model.Usuario;

@Service
public class JwtService {
  private final JwtEncoder encoder;

  public JwtService(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  public String generateToken(Usuario usuario) {
    Instant now = Instant.now();
    long expiry = 36000L;

    String scope = usuario
        .getAuthorities().stream()
        .map(auth->auth.getAuthority())
        .collect(Collectors
            .joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("client-jwt-1.0")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(usuario.getUsername())
        .claim("usrd", usuario.getId())
        .claim("scope", scope)
        .build();

    return encoder.encode(
        JwtEncoderParameters.from(claims))
        .getTokenValue();
  }

}
