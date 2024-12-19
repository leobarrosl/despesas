package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import com.leonardo.despesas.models.entities.Lancamento;
import com.leonardo.despesas.models.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LancamentosRepository extends JpaRepository<Lancamento, Integer> {

    List<Lancamento> findByPerfil(Perfil perfil);

    Optional<Lancamento> findById(Integer id);

    List<Lancamento> findByTipoPagamento(TipoPagamentoEnum tipo);
}
