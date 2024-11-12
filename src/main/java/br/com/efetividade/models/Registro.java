package br.com.efetividade.models;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registros")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date data;

    private String status;

    @ManyToOne
    @JoinColumn(name = "vigilante_id", nullable = true, updatable = false)
    private Vigilante vigilante;

    @ManyToOne
    @JoinColumn(name = "substituto_id", nullable = true, updatable = false)
    private Vigilante substituto;

    private String descontoHora;

    public boolean comparar(Registro registro) {
        Calendar calendarThis = Calendar.getInstance();
        Calendar calendarRegistro = Calendar.getInstance();
        calendarThis.setTime(this.getData());
        calendarRegistro.setTime(registro.getData());

        if ((this.getVigilante().getMatricula().equals(registro.getVigilante().getMatricula()))
                && (calendarThis.get(Calendar.DAY_OF_MONTH) == calendarRegistro.get(Calendar.DAY_OF_MONTH))
                && (calendarThis.get(Calendar.MONTH) == calendarRegistro.get(Calendar.MONTH))
                && (calendarThis.get(Calendar.YEAR) == calendarRegistro.get(Calendar.YEAR))) {
            return true;
        } else {
            return false;
        }

    }

}
