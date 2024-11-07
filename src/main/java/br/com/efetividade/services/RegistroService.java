package br.com.efetividade.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.efetividade.excepions.ItemNotFoundExcepion;
import br.com.efetividade.models.Registro;
import br.com.efetividade.repositories.RegistroRepository;

@Service
public class RegistroService {
    @Autowired
    RegistroRepository registroRepository;

    public Registro get(Long id) throws ItemNotFoundExcepion {
        Optional<Registro> registro = registroRepository.findById(id);
        return registro.orElseThrow(() -> new ItemNotFoundExcepion(id, Registro.class.getSimpleName()));
    }

    public List<Registro> all() {
        return registroRepository.findAll();
    }

    public Registro update(Registro registro, Long id) throws ItemNotFoundExcepion {
        Registro registroOld = this.get(id);
        registroOld = registro;
        return registroRepository.save(registroOld);
    }

    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    public void delete(Registro registro) {
        registroRepository.delete(registro);
    }

    public void delete(Long id) throws ItemNotFoundExcepion {
        registroRepository.delete(this.get(id));
    }

}
