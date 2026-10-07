package com.MarleyCyberLab.agendador_horarios.infrastructure.repository;

import com.MarleyCyberLab.agendador_horarios.infrastructure.entity.Agendamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByProfissionalAndDataHoraAgendamentoLessThanAndDataHoraAgendamentoGreaterThan(
            String profissional, LocalDateTime fimDoNovoAgendamento, LocalDateTime inicioDoNovoAgendamento);
    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento,String cliente);

    List<Agendamento> findByDataHoraAgendamentoGreaterThanEqualAndDataHoraAgendamentoLessThan(
            LocalDateTime inicioDoDia, LocalDateTime inicioDoProximoDia);

    Agendamento findByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento,String cliente);
}
