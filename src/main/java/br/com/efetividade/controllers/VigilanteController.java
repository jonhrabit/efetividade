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
import br.com.efetividade.models.Vigilante;
import br.com.efetividade.services.VigilanteService;

@RestController
@RequestMapping("/vigilantes/")
public class VigilanteController {
@Autowired
VigilanteService vigilanteService;
@GetMapping
public List<Vigilante> all() {
return vigilanteService.all();
}
@PostMapping
public Vigilante post(@RequestBody Vigilante vigilante) {
return vigilanteService.save(vigilante);
}
@GetMapping("/{id}")
public Vigilante get(@PathVariable Long id) throws Throwable {
return vigilanteService.get(id);
}
@PutMapping("/{id}")
Vigilante put(@RequestBody Vigilante novo, @PathVariable Long id) throws ItemNotFoundExcepion {
return vigilanteService.update(novo, id);
}
@DeleteMapping("/{id}")
public boolean delete(@PathVariable Long id) throws ItemNotFoundExcepion  {
vigilanteService.delete(id);
return true;
}
}

