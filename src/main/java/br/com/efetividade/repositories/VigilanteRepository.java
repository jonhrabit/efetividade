package br.com.efetividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.efetividade.models.Vigilante;

public interface VigilanteRepository extends JpaRepository<Vigilante, Long>{

}
