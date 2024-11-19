package br.com.efetividade.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.efetividade.models.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
    
    public List<Registro> findByDataBetween(Date inicio, Date fim);

}
