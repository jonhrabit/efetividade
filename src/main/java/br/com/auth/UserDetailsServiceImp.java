package br.com.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.auth.model.Usuario;
import br.com.auth.repositories.UsuarioRepository;
import br.com.efetividade.excepions.ItemNotFoundExcepion;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário %s não localizado.".formatted(username)));

        return usuario;
    }

    public Usuario get(Long id) throws ItemNotFoundExcepion {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ItemNotFoundExcepion(id, Usuario.class.getSimpleName()));
    }

    public List<Usuario> all() {
        return usuarioRepository.findAll();
    }

    public Usuario update(Usuario usuario, Long id) throws ItemNotFoundExcepion {
        Usuario usuarioOld = this.get(id);
        usuarioOld = usuario;
        return usuarioRepository.save(usuarioOld);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public void delete(Long id) throws ItemNotFoundExcepion {
        usuarioRepository.delete(this.get(id));
    }

}
