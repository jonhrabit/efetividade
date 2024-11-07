package br.com.auth.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.auth.DTO.LoginDTO;
import br.com.auth.model.Permissao;
import br.com.auth.model.Usuario;
import br.com.auth.repositories.PermissaoRepository;
import br.com.auth.repositories.UsuarioRepository;
import br.com.auth.services.AuthenticationService;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PermissaoRepository permissaoRepository;

    @PostMapping("login")
    public ResponseEntity<Object> Login(@RequestBody LoginDTO login) {

        var user = usuarioRepository.findByUsername(login.username());
        if (user.isEmpty()) {
            return new ResponseEntity<Object>("Usuário não localizado.", HttpStatus.UNAUTHORIZED);
        }
        if (!user.get().matches(login.password())) {
            return new ResponseEntity<Object>("Senha incorreta.", HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<Object>(authenticationService.authenticate(user.get()), HttpStatus.OK);
    }

    @RequestMapping("/primeiroacesso")
    public @ResponseBody ResponseEntity<Object> primeiroAcesso() {
        String senha = "adm";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(
                null,
                "adm",
                encodedPassword,
                new ArrayList<Permissao>());
        List<Permissao> lista = new ArrayList<Permissao>();
        Permissao perm = permissaoRepository.save(new Permissao(null, "BASIC"));
        lista.add(perm);
        usuario.setPermissoes(lista);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("primeiro acesso realizado com sucesso.");
    }
}
