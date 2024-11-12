package br.com.efetividade.DTO;

import java.util.List;

import br.com.efetividade.models.Registro;

public record RegistroPostRespost(List<String> erros, List<Registro> sucessos) {

}
