package br.com.efetividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.efetividade.models.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

}
