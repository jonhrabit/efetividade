package br.com.efetividade.controllers;

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

import br.com.efetividade.excepions.ItemNotFoundExcepion;
import br.com.efetividade.models.Registro;
import br.com.efetividade.services.RegistroService;

@RestController
@RequestMapping("/registros/")
public class RegistroController {
    @Autowired
    RegistroService registroService;

    @GetMapping
    public List<Registro> all() {
        return registroService.all();
    }

    @PostMapping
    public Registro post(@RequestBody Registro registro) {
        return registroService.save(registro);
    }

    @GetMapping("/{id}")
    public Registro get(@PathVariable Long id) throws Throwable {
        return registroService.get(id);
    }

    @PutMapping("/{id}")
    Registro put(@RequestBody Registro novo, @PathVariable Long id) throws ItemNotFoundExcepion {
        return registroService.update(novo, id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ItemNotFoundExcepion {
        registroService.delete(id);
        return true;
    }
}
