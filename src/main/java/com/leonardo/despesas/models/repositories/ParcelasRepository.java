package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.entities.Parcela;
import com.leonardo.despesas.models.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParcelasRepository extends JpaRepository<Parcela, Integer> {

    @Query("""
            SELECT pa 
            FROM Parcela as pa 
            JOIN Lancamento as l ON pa.lancamento = l 
            JOIN Perfil as p ON l.perfil = p 
            WHERE l.perfil = :perfil AND 
            MONTH(pa.dataPagamento) = :month AND 
            YEAR(pa.dataPagamento) = :year
            """)
    List<Parcela> findByPerfilAndMonth(Perfil perfil, Integer month, Integer year);
}
