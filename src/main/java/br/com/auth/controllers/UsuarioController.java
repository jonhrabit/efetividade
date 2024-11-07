package br.com.auth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.auth.UserDetailsServiceImp;
import br.com.auth.model.Usuario;
import br.com.efetividade.excepions.ItemNotFoundExcepion;

@RestController
@RequestMapping("/usuarios/")
public class UsuarioController {
    @Autowired
    UserDetailsServiceImp usuarioService;

    @GetMapping
    public List<Usuario> all() {
        return usuarioService.all();
    }

    @PostMapping
    public Usuario post(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario get(@PathVariable Long id) throws Throwable {
        return usuarioService.get(id);
    }

    @PutMapping("/{id}")
    Usuario put(@RequestBody Usuario novo, @PathVariable Long id) throws ItemNotFoundExcepion {
        return usuarioService.update(novo, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ItemNotFoundExcepion {
        usuarioService.delete(id);
        return true;
    }
}
