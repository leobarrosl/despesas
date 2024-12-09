package com.leonardo.despesas.repositories;

import com.leonardo.despesas.models.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelasRepository extends JpaRepository<Parcela, Integer> {
}
