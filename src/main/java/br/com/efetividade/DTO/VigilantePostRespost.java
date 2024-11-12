package br.com.efetividade.DTO;

import java.util.List;

import br.com.efetividade.models.Vigilante;

public record VigilantePostRespost(List<Vigilante> erros, List<Vigilante> sucessos) {

}
