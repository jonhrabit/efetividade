package br.com.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.auth.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
    public Optional<Permissao> findByNome(String permissao);
    
}
