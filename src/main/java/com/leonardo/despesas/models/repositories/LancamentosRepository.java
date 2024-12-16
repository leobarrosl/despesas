package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.entities.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentosRepository extends JpaRepository<Lancamento, Integer> {
}
