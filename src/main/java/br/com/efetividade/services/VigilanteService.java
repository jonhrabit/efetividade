package br.com.efetividade.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.efetividade.excepions.ItemNotFoundExcepion;
import br.com.efetividade.models.Vigilante;
import br.com.efetividade.repositories.VigilanteRepository;

@Service
public class VigilanteService {
    @Autowired
    VigilanteRepository vigilanteRepository;

    public Vigilante get(Long id) throws ItemNotFoundExcepion {
        Optional<Vigilante> vigilante = vigilanteRepository.findById(id);
        return vigilante.orElseThrow(() -> new ItemNotFoundExcepion(id, Vigilante.class.getSimpleName()));
    }

    public List<Vigilante> all() {
        return vigilanteRepository.findAll();
    }
    public List<Vigilante> allAtivos() {
        return vigilanteRepository.findByAtivo(true);
    }

    public Vigilante getByMatricula(String matricula) throws ItemNotFoundExcepion {
        try {
            return vigilanteRepository.findAll().stream().filter(v -> v.getMatricula().equals(matricula)).findFirst()
                    .orElseThrow();
        } catch (Exception e) {
            throw new ItemNotFoundExcepion(0L, "Matricula " + matricula + "não localizada.");
        }
    }

    public Vigilante update(Vigilante vigilante, Long id) throws ItemNotFoundExcepion {
        Vigilante vigilanteOld = this.get(id);
        vigilanteOld = vigilante;
        return vigilanteRepository.save(vigilanteOld);
    }

    public Vigilante save(Vigilante vigilante) throws Exception {
        if (this.all().stream().filter(vig -> vig.comparar(vigilante)).count() > 0) {
            throw new Exception(
                    "(" + vigilante.getCpf() + " - " + vigilante.getMatricula() + ")"
                            + "Já há um vigilante cadastrado com esta matricula ou CPF");
        }
        return vigilanteRepository.save(vigilante);
    }

    public void delete(Vigilante vigilante) {
        vigilanteRepository.delete(vigilante);
    }

    public void delete(Long id) throws ItemNotFoundExcepion {
        vigilanteRepository.delete(this.get(id));
    }

    public List<Vigilante> pendencia() {
        return this.all().stream().filter(vig -> (vig.getCpf() == null) || (vig.getNome() == null)).toList();
    }

}
