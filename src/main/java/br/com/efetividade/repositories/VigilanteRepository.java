package br.com.efetividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.efetividade.models.Vigilante;
import java.util.List;


public interface VigilanteRepository extends JpaRepository<Vigilante, Long>{

    List<Vigilante> findByAtivo(boolean ativo);

}
