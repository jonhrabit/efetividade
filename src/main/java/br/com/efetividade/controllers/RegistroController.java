package br.com.efetividade.controllers;

import java.util.ArrayList;
import java.util.Date;
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

import br.com.efetividade.DTO.RegistroPostRespost;
import br.com.efetividade.excepions.ItemNotFoundExcepion;
import br.com.efetividade.models.Registro;
import br.com.efetividade.models.Vigilante;
import br.com.efetividade.services.RegistroService;
import br.com.efetividade.services.VigilanteService;

@RestController
@RequestMapping("/registros/")
public class RegistroController {
    @Autowired
    RegistroService registroService;

    @Autowired
    VigilanteService vigilanteService;

    @GetMapping
    public List<Registro> all() {
        return registroService.all();
    }

    @PostMapping
    public RegistroPostRespost post(@RequestBody List<Registro> registros) {
        RegistroPostRespost resposta = new RegistroPostRespost(new ArrayList<String>(),
                new ArrayList<Registro>());
        registros.forEach(reg -> {
            try {
                resposta.sucessos().add(registroService.save(reg));
            } catch (Exception e) {
                resposta.erros().add(
                        reg.getData().toString() + " - " + reg.getVigilante().getMatricula() + " - " + e.getMessage());
            }
        });
        return resposta;
    }

    @PostMapping("/import")
    public RegistroPostRespost importEfetividade(@RequestBody String efetividade) {
        RegistroPostRespost resposta = new RegistroPostRespost(new ArrayList<String>(),
                new ArrayList<Registro>());
        Date data = new Date();
        String[] resultado = efetividade.split("\n");
        for (String linha : resultado) {
            if (linha != "") {
                String[] palavras = linha.split(" ");
                switch (palavras[0].toLowerCase()) {
                    case "data":
                        data = registroService.getDataRegistro(palavras[1]);
                        break;
                    case "":
                        break;
                    default:
                        Vigilante vigilante = new Vigilante();
                        Vigilante substituto = new Vigilante();
                        int countTentativas = 0;
                        String status = "Ativo";
                        for (int i = 0; palavras.length > i; i++) {
                            try {
                                vigilante = vigilanteService.getByMatricula(palavras[i]);
                                if ((linha.indexOf("falta") != -1) || (linha.indexOf("reciclagem") != -1)) {
                                    int countSubstitutoTentativas = i + 1;
                                    status = "falta";
                                    for (int z = i + 1; palavras.length > z; z++) {
                                        try {
                                            substituto = vigilanteService.getByMatricula(palavras[z]);
                                            break;
                                        } catch (Exception e) {
                                            countSubstitutoTentativas++;
                                            if (countSubstitutoTentativas > -palavras.length) {
                                                substituto = null;
                                                resposta.erros()
                                                        .add("Não localizada matricula do substituto na linha: "
                                                                + linha);
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    substituto = null;
                                }

                                break;
                            } catch (ItemNotFoundExcepion e) {
                                countTentativas++;
                                if (countTentativas >= palavras.length) {
                                    resposta.erros().add("Matricula válida não localizada na linha: " + linha);
                                    vigilante = null;
                                    break;
                                }
                            }
                        }
                        if (vigilante != null) {

                            try {
                                registroService.save(new Registro(null, data, status, vigilante, substituto, null));
                                resposta.sucessos().add(new Registro(null, data, status, vigilante, substituto, null));
                            } catch (Exception e) {
                                resposta.erros().add(e.getMessage());
                            }
                        }
                        break;
                }
            }
        }

        return resposta;
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
