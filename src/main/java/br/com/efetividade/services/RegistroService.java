package br.com.efetividade.services;

import java.util.Calendar;
import java.util.Date;
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

    public Registro save(Registro registro) throws Exception {
        if (this.all().stream().filter(reg -> reg.comparar(registro)).count() > 0) {
            throw new Exception("Já existe um regisrtro com este vigilante nesta data - " + registro.getData() + " - "
                    + registro.getVigilante().getGuerra());
        }
        return registroRepository.save(registro);
    }

    public void delete(Registro registro) {
        registroRepository.delete(registro);
    }

    public void delete(Long id) throws ItemNotFoundExcepion {
        registroRepository.delete(this.get(id));
    }

    public Date getDataRegistro(String string) {
        String[] i = string.split("/");
        Calendar calendar = Calendar.getInstance();
        int mes = Integer.parseInt(i[1]) - 1;
        calendar.set(Integer.parseInt(i[2]), mes, Integer.parseInt(i[0]), 1, 0);
        return calendar.getTime();
    }

    public List<Registro> findByDateBetween(Date inicio, Date fim) {
        return registroRepository.findByDataBetween(inicio, fim);
    }

    public List<Registro> queryByMes(int mes, int ano) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, 1, 00, 01);
        Date d1 = calendar.getTime();
        int ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(ano, mes, ultimoDia, 23, 59);
        Date d2 = calendar.getTime();
        return registroRepository.findByDataBetween(d1, d2);
    }

}
