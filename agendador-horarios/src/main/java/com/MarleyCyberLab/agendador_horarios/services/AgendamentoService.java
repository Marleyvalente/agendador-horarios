package com.MarleyCyberLab.agendador_horarios.services;

import com.MarleyCyberLab.agendador_horarios.infrastructure.entity.Agendamento;
import com.MarleyCyberLab.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;


@Service
@RequiredArgsConstructor

public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento){

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);


      boolean horarioOcupado = agendamentoRepository
              .existsByProfissionalAndDataHoraAgendamentoLessThanAndDataHoraAgendamentoGreaterThan(
                      agendamento.getProfissional(), horaFim, horaAgendamento);

      if (horarioOcupado) {
          throw new IllegalStateException("Horário já está preenchido para este profissional");
      }
      return agendamentoRepository.save(agendamento);


    }

    public void deletarAgendamento (LocalDateTime dataHoraAgendamento, String cliente){
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

    }

    public List<Agendamento> buscarAgendamentosDia(LocalDate data)
    {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime inicioDoProximoDia = data.plusDays(1).atStartOfDay();

        return agendamentoRepository.findByDataHoraAgendamentoGreaterThanEqualAndDataHoraAgendamentoLessThan(
                primeiraHoraDia, inicioDoProximoDia);

    }

    public Agendamento alterarAgendamento(Agendamento agendamento, String cliente, LocalDateTime dataHoraAgendamento){
       Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

       if(Objects.isNull(agenda)){
           throw new RuntimeException("Horário não está preenchido");
       }


       agendamento.setId(agenda.getId());
      return agendamentoRepository.save(agendamento);

    }
}
